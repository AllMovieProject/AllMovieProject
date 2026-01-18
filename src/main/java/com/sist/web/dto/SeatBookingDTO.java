package com.sist.web.dto;

import com.sist.web.vo.ScheduleSeatVO;

import lombok.Data;

@Data
public class SeatBookingDTO {
    private int schedule_id;
    private String user_id;
    private ScheduleSeatVO ssvo;
}
