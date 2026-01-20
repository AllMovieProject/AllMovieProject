package com.sist.web.vo;

import lombok.Data;

@Data
public class BookingVO {
	private int schedule_id, cancel_flag;
	private String booking_id, member_id;
}
