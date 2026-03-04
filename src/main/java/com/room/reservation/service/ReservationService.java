package com.room.reservation.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room.reservation.dto.BillResponse;
import com.room.reservation.dto.CreateReservationRequest;
import com.room.reservation.dto.ReservationResponse;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomType;
import com.room.reservation.exception.BadRequestException;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.repository.RoomTypeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomTypeRepository roomTypeRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomTypeRepository roomTypeRepository) {
        this.reservationRepository = reservationRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest req) {

        LocalDate checkIn = parseDate(req.getCheckIn(), "checkIn must be YYYY-MM-DD");
        LocalDate checkOut = parseDate(req.getCheckOut(), "checkOut must be YYYY-MM-DD");

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("checkOut must be after checkIn");
        }

        RoomType roomType = roomTypeRepository.findById(req.getRoomTypeId())
                .orElseThrow(() -> new NotFoundException("Room type not found"));

        long nightsLong = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        int nights = (int) nightsLong;

        BigDecimal total = roomType.getRatePerNight().multiply(BigDecimal.valueOf(nights));

        Reservation reservation = new Reservation();
        reservation.setGuestName(req.getGuestName().trim());
        reservation.setGuestAddress(req.getGuestAddress().trim());
        reservation.setContactNo(req.getContactNo().trim());
        reservation.setRoomType(roomType);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);
        reservation.setTotalNights(nights);
        reservation.setTotalAmount(total);
        reservation.setStatus("ACTIVE");

        // Save first to get ID, then generate reservation_no
        reservation = reservationRepository.save(reservation);

        String reservationNo = generateReservationNo(reservation.getId());
        reservation.setReservationNo(reservationNo);

        reservation = reservationRepository.save(reservation);

        return toResponse(reservation);
    }

    public ReservationResponse getReservation(String reservationNo) {
        Reservation reservation = reservationRepository.findByReservationNo(reservationNo)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        return toResponse(reservation);
    }

    public BillResponse getBill(String reservationNo) {
        Reservation reservation = reservationRepository.findByReservationNo(reservationNo)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        RoomType roomType = reservation.getRoomType();

        return new BillResponse(
                reservation.getReservationNo(),
                reservation.getGuestName(),
                reservation.getContactNo(),
                roomType.getName(),
                reservation.getCheckIn().toString(),
                reservation.getCheckOut().toString(),
                reservation.getTotalNights(),
                roomType.getRatePerNight(),
                reservation.getTotalAmount()
        );
    }

    private ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(
                r.getReservationNo(),
                r.getGuestName(),
                r.getRoomType().getName(),
                r.getCheckIn().toString(),
                r.getCheckOut().toString(),
                r.getTotalNights(),
                r.getTotalAmount(),
                r.getStatus()
        );
    }

    private LocalDate parseDate(String value, String errorMsg) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException(errorMsg);
        }
    }

    private String generateReservationNo(Long id) {
        // Example: RES-000001
        return "RES-" + String.format("%06d", id);
    }
}