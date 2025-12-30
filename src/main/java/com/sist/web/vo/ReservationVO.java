package com.sist.web.vo;

import lombok.Data;

@Data
public class ReservationVO {
	private int reservation_id, schedule_id, cancel_flag;
	private String member_id;
}
