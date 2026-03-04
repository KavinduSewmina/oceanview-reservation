package com.room.reservation.dto.report;

import java.math.BigDecimal;

public class RevenueReportResponse {

    private String from;
    private String to;
    private long reservationCount;
    private BigDecimal totalRevenue;

    public RevenueReportResponse() {}

    public RevenueReportResponse(String from, String to, long reservationCount, BigDecimal totalRevenue) {
        this.from = from;
        this.to = to;
        this.reservationCount = reservationCount;
        this.totalRevenue = totalRevenue;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public long getReservationCount() { return reservationCount; }
    public void setReservationCount(long reservationCount) { this.reservationCount = reservationCount; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}