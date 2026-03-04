package com.room.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.BillResponse;
import com.room.reservation.service.ReservationService;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final ReservationService reservationService;

    public BillingController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{reservationNo}")
    public ResponseEntity<BillResponse> getBill(@PathVariable String reservationNo) {
        return ResponseEntity.ok(reservationService.getBill(reservationNo));
    }
}