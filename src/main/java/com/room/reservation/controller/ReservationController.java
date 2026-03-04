package com.room.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.CreateReservationRequest;
import com.room.reservation.dto.ReservationResponse;
import com.room.reservation.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Add reservation
    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody CreateReservationRequest req) {
        return ResponseEntity.ok(reservationService.createReservation(req));
    }

    // Search reservation by reservation number
    @GetMapping("/{reservationNo}")
    public ResponseEntity<ReservationResponse> get(@PathVariable String reservationNo) {
        return ResponseEntity.ok(reservationService.getReservation(reservationNo));
    }
}