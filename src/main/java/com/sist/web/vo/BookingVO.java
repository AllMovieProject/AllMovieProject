package com.sist.web.vo;

import lombok.Data;

@Data
public class BookingVO {
	private int booking_id, schedule_id, cancel_flag;
	private String member_id;
}
