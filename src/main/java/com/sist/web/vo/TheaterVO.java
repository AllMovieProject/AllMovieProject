package com.sist.web.vo;

import lombok.Data;

@Data
public class TheaterVO {
	private int theater_lat, theater_lng;
	private String theater_id, theater_name, theater_region, theater_address;
}
