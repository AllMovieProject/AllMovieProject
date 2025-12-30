package com.sist.web.vo;

import lombok.Data;

@Data
public class SeatVO {
	private int seat_id, screen_id, seat_col;
	private String seat_row, seat_satus;
}
