package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {
	
	@GetMapping("/booking")
	public String booking_main(Model model) {
		model.addAttribute("main_jsp", "../booking/booking.jsp");
		return "main/main";
	}
	
	@GetMapping("/booking/seat")
	public String booking_seat(Model model) {
		model.addAttribute("main_jsp", "../booking/seat.jsp");
		return "main/main";
	}
}
