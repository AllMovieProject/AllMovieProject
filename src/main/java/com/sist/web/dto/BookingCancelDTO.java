package com.sist.web.dto;

import lombok.Data;

@Data
public class BookingCancelDTO {
    private int scheduleId;
    private String bookingId;
}
