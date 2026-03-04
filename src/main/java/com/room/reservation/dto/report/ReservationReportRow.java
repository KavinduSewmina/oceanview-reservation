package com.room.reservation.dto.report;

import java.math.BigDecimal;

public class ReservationReportRow {

    private String reservationNo;
    private String guestName;
    private String roomType;
    private String checkIn;
    private String checkOut;
    private Integer totalNights;
    private BigDecimal totalAmount;
    private String status;

    public ReservationReportRow() {}

    public ReservationReportRow(String reservationNo, String guestName, String roomType,
                                String checkIn, String checkOut, Integer totalNights,
                                BigDecimal totalAmount, String status) {
        this.reservationNo = reservationNo;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalNights = totalNights;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public Integer getTotalNights() { return totalNights; }
    public void setTotalNights(Integer totalNights) { this.totalNights = totalNights; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}