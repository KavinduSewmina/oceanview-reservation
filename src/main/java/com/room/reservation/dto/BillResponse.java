package com.room.reservation.dto;

import java.math.BigDecimal;

public class BillResponse {

    private String reservationNo;
    private String guestName;
    private String contactNo;
    private String roomType;

    private String checkIn;
    private String checkOut;

    private Integer totalNights;
    private BigDecimal ratePerNight;
    private BigDecimal totalAmount;

    public BillResponse() {}

    public BillResponse(String reservationNo, String guestName, String contactNo, String roomType,
                        String checkIn, String checkOut, Integer totalNights,
                        BigDecimal ratePerNight, BigDecimal totalAmount) {
        this.reservationNo = reservationNo;
        this.guestName = guestName;
        this.contactNo = contactNo;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalNights = totalNights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = totalAmount;
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public Integer getTotalNights() { return totalNights; }
    public void setTotalNights(Integer totalNights) { this.totalNights = totalNights; }

    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}