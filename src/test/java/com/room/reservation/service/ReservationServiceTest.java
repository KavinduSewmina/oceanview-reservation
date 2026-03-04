package com.room.reservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.room.reservation.dto.CreateReservationRequest;
import com.room.reservation.dto.ReservationResponse;
import com.room.reservation.entity.Reservation;
import com.room.reservation.entity.RoomType;
import com.room.reservation.exception.BadRequestException;
import com.room.reservation.exception.NotFoundException;
import com.room.reservation.repository.ReservationRepository;
import com.room.reservation.repository.RoomTypeRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private ReservationService reservationService;

    private RoomType roomType;

    @BeforeEach
    void setup() {
        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setName("Single");
        roomType.setRatePerNight(new BigDecimal("5000.00"));
        roomType.setCapacity(1);
    }

    @Test
    void createReservation_shouldCalculateNightsAndTotalAmount() {
        // Arrange
        CreateReservationRequest req = new CreateReservationRequest();
        req.setGuestName("Test User");
        req.setGuestAddress("Negombo");
        req.setContactNo("0771234567");
        req.setRoomTypeId(1L);
        req.setCheckIn("2026-03-10");
        req.setCheckOut("2026-03-12"); // 2 nights

        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));

        // First save -> returns entity with generated ID
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            if (r.getId() == null) r.setId(1L);  // simulate DB generated id
            return r;
        });

        // Act
        ReservationResponse res = reservationService.createReservation(req);

        // Assert
        assertNotNull(res.getReservationNo());
        assertEquals(2, res.getTotalNights());
        assertEquals(new BigDecimal("10000.00"), res.getTotalAmount());
        assertEquals("ACTIVE", res.getStatus());
    }

    @Test
    void createReservation_shouldThrowBadRequest_whenCheckOutNotAfterCheckIn() {
        // Arrange
        CreateReservationRequest req = new CreateReservationRequest();
        req.setGuestName("Test User");
        req.setGuestAddress("Negombo");
        req.setContactNo("0771234567");
        req.setRoomTypeId(1L);
        req.setCheckIn("2026-03-10");
        req.setCheckOut("2026-03-10"); // invalid

        // Act + Assert
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservationService.createReservation(req));

        assertTrue(ex.getMessage().toLowerCase().contains("checkout"));
    }

    @Test
    void createReservation_shouldThrowNotFound_whenRoomTypeMissing() {
        // Arrange
        CreateReservationRequest req = new CreateReservationRequest();
        req.setGuestName("Test User");
        req.setGuestAddress("Negombo");
        req.setContactNo("0771234567");
        req.setRoomTypeId(99L);
        req.setCheckIn("2026-03-10");
        req.setCheckOut("2026-03-12");

        when(roomTypeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NotFoundException.class, () -> reservationService.createReservation(req));
    }

    @Test
    void createReservation_shouldGenerateShortReservationNumber() {
        // Arrange
        CreateReservationRequest req = new CreateReservationRequest();
        req.setGuestName("Test User");
        req.setGuestAddress("Negombo");
        req.setContactNo("0771234567");
        req.setRoomTypeId(1L);
        req.setCheckIn("2026-03-10");
        req.setCheckOut("2026-03-12");

        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));

        // simulate DB id = 7
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            if (r.getId() == null) r.setId(7L);
            return r;
        });

        // Act
        ReservationResponse res = reservationService.createReservation(req);

        // Assert
        assertEquals("RES-007", res.getReservationNo());
    }
}