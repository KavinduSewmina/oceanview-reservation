package com.room.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNo(String reservationNo);

    boolean existsByReservationNo(String reservationNo);

    // ✅ Report A: reservations by check-in date range
    List<Reservation> findByCheckInBetween(LocalDate from, LocalDate to);

    // ✅ Report C: upcoming check-ins (next 7 days)
    List<Reservation> findByCheckInBetweenAndStatus(LocalDate from, LocalDate to, String status);
}