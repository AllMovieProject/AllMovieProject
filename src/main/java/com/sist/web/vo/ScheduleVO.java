package com.sist.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ScheduleVO {
	private int schedule_id, movie_id, screen_id;
	private Date schedule_date;
	private String string_schedule_date;
}
