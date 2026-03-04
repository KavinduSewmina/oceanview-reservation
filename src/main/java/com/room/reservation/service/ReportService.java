package com.room.reservation.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room.reservation.dto.report.ReservationReportRow;
import com.room.reservation.dto.report.RevenueReportResponse;
import com.room.reservation.entity.Reservation;
import com.room.reservation.exception.BadRequestException;
import com.room.reservation.repository.ReservationRepository;

@Service
public class ReportService {

    private final ReservationRepository reservationRepository;

    public ReportService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // ✅ Report A: Reservations by date range (check-in range)
    @Transactional(readOnly = true)
    public List<ReservationReportRow> reservationsByDateRange(String from, String to) {
        LocalDate fromDate = parseDate(from, "from must be YYYY-MM-DD");
        LocalDate toDate = parseDate(to, "to must be YYYY-MM-DD");

        if (toDate.isBefore(fromDate)) {
            throw new BadRequestException("to date must be after or equal to from date");
        }

        List<Reservation> list = reservationRepository.findByCheckInBetween(fromDate, toDate);

        return list.stream().map(r -> new ReservationReportRow(
                r.getReservationNo(),
                r.getGuestName(),
                r.getRoomType().getName(),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                r.getTotalNights(),
                r.getTotalAmount(),
                r.getStatus()
        )).toList();
    }

    // ✅ Report B: Revenue summary by date range (check-in range)
    @Transactional(readOnly = true)
    public RevenueReportResponse revenueByDateRange(String from, String to) {
        LocalDate fromDate = parseDate(from, "from must be YYYY-MM-DD");
        LocalDate toDate = parseDate(to, "to must be YYYY-MM-DD");

        if (toDate.isBefore(fromDate)) {
            throw new BadRequestException("to date must be after or equal to from date");
        }

        List<Reservation> list = reservationRepository.findByCheckInBetween(fromDate, toDate);

        BigDecimal totalRevenue = list.stream()
                .filter(r -> r.getTotalAmount() != null)
                .map(Reservation::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RevenueReportResponse(
                fromDate.toString(),
                toDate.toString(),
                list.size(),
                totalRevenue
        );
    }

    // ✅ Report C: Upcoming check-ins (next 7 days)
    @Transactional(readOnly = true)
    public List<ReservationReportRow> upcomingCheckins7Days() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(7);

        List<Reservation> list = reservationRepository.findByCheckInBetweenAndStatus(today, end, "ACTIVE");

        return list.stream().map(r -> new ReservationReportRow(
                r.getReservationNo(),
                r.getGuestName(),
                r.getRoomType().getName(),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                r.getTotalNights(),
                r.getTotalAmount(),
                r.getStatus()
        )).toList();
    }

    private LocalDate parseDate(String value, String errorMsg) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException(errorMsg);
        }
    }
}