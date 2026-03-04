package com.room.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.room.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationNo(String reservationNo);
    boolean existsByReservationNo(String reservationNo);
}