package com.room.reservation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.room.reservation.dto.report.ReservationReportRow;
import com.room.reservation.dto.report.RevenueReportResponse;
import com.room.reservation.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Report A
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationReportRow>> reservations(@RequestParam String from,
                                                                   @RequestParam String to) {
        return ResponseEntity.ok(reportService.reservationsByDateRange(from, to));
    }

    // Report B
    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportResponse> revenue(@RequestParam String from,
                                                         @RequestParam String to) {
        return ResponseEntity.ok(reportService.revenueByDateRange(from, to));
    }

    // Report C
    @GetMapping("/upcoming-checkins")
    public ResponseEntity<List<ReservationReportRow>> upcoming() {
        return ResponseEntity.ok(reportService.upcomingCheckins7Days());
    }
}