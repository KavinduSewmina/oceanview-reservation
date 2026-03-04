package com.room.reservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.room.reservation.dto.report.ReservationReportRow;
import com.room.reservation.dto.report.RevenueReportResponse;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomType;
import com.room.reservation.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void revenueByDateRange_shouldSumTotalAmount() {
        // Arrange
        RoomType rt = new RoomType();
        rt.setName("Double");
        rt.setRatePerNight(new BigDecimal("8000.00"));

        Reservation r1 = new Reservation();
        r1.setReservationNo("RES-001");
        r1.setGuestName("A");
        r1.setRoomType(rt);
        r1.setCheckIn(LocalDate.parse("2026-03-10"));
        r1.setCheckOut(LocalDate.parse("2026-03-12"));
        r1.setTotalNights(2);
        r1.setTotalAmount(new BigDecimal("16000.00"));
        r1.setStatus("ACTIVE");

        Reservation r2 = new Reservation();
        r2.setReservationNo("RES-002");
        r2.setGuestName("B");
        r2.setRoomType(rt);
        r2.setCheckIn(LocalDate.parse("2026-03-15"));
        r2.setCheckOut(LocalDate.parse("2026-03-16"));
        r2.setTotalNights(1);
        r2.setTotalAmount(new BigDecimal("8000.00"));
        r2.setStatus("ACTIVE");

        when(reservationRepository.findByCheckInBetween(
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-31")
        )).thenReturn(List.of(r1, r2));

        // Act
        RevenueReportResponse resp = reportService.revenueByDateRange("2026-03-01", "2026-03-31");

        // Assert
        assertEquals(2, resp.getReservationCount());
        assertEquals(new BigDecimal("24000.00"), resp.getTotalRevenue());
    }

    @Test
    void reservationsByDateRange_shouldReturnRows() {
        // Arrange
        RoomType rt = new RoomType();
        rt.setName("Single");
        rt.setRatePerNight(new BigDecimal("5000.00"));

        Reservation r = new Reservation();
        r.setReservationNo("RES-003");
        r.setGuestName("Test User");
        r.setRoomType(rt);
        r.setCheckIn(LocalDate.parse("2026-03-05"));
        r.setCheckOut(LocalDate.parse("2026-03-06"));
        r.setTotalNights(1);
        r.setTotalAmount(new BigDecimal("5000.00"));
        r.setStatus("ACTIVE");

        when(reservationRepository.findByCheckInBetween(
                LocalDate.parse("2026-03-01"),
                LocalDate.parse("2026-03-31")
        )).thenReturn(List.of(r));

        // Act
        List<ReservationReportRow> rows = reportService.reservationsByDateRange("2026-03-01", "2026-03-31");

        // Assert
        assertEquals(1, rows.size());
        assertEquals("RES-003", rows.get(0).getReservationNo());
        assertEquals("Single", rows.get(0).getRoomType());
        assertEquals(new BigDecimal("5000.00"), rows.get(0).getTotalAmount());
    }
}