package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.vo.ScheduleSeatVO;

@Controller
public class BookingController {

	@GetMapping("/booking")
	public String booking_main(Model model) {
		model.addAttribute("main_jsp", "../booking/booking.jsp");
		model.addAttribute("today", getToday());
		return "main/main";
	}

	private String getToday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = df.format(date);
		StringTokenizer st = new StringTokenizer(today, "-");

		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());

		today = String.format("%04d-%02d-%02d", year, month, day);
		return today;
	}

	@PostMapping("/booking/seat")
	public String booking_seat(@RequestParam("id") int id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("main_jsp", "../booking/seat.jsp");
		return "main/main";
	}

	@PostMapping("/booking/payment")
	public String booking_payment(@RequestParam("schedule_id") int schedule_id, @RequestParam("user_id") String user_id,
			 Model model) {
		model.addAttribute("scheduel_id", schedule_id);
		model.addAttribute("user_id", user_id);
		model.addAttribute("main_jsp", "../booking/payment.jsp");
		return "main/main";
	}
}
