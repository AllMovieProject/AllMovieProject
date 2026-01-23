package com.sist.web.dto;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private String date;
    private Integer movie;
    private Integer region;
    private String theater;
    private Integer schedule_id;
}
