package com.room.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateReservationRequest {

    @NotBlank
    @Size(max = 120)
    private String guestName;

    @NotBlank
    @Size(max = 255)
    private String guestAddress;

    @NotBlank
    @Size(max = 30)
    private String contactNo;

    @NotNull
    private Long roomTypeId;

    @NotBlank
    private String checkIn;   // "YYYY-MM-DD"

    @NotBlank
    private String checkOut;  // "YYYY-MM-DD"

    public CreateReservationRequest() {}

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getGuestAddress() { return guestAddress; }
    public void setGuestAddress(String guestAddress) { this.guestAddress = guestAddress; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public Long getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
}