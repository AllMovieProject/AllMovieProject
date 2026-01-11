package com.sist.web.dto;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private Integer page;
    private String date;
    private Integer movie;
    private Integer region;
    private String theater;
}
