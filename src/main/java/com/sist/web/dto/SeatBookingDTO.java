package com.sist.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeatBookingDTO {
    private Integer schedule_id;
    private List<Integer> selected_seats;
}
