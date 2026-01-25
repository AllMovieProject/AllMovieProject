package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    @GetMapping("/booking")
    public String booking_main(Model model) {
        model.addAttribute("main_jsp", "../booking/booking.jsp");
        return "main/main";
    }

    @PostMapping("/booking/seat")
	public String booking_seat(@RequestParam("id") int id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("main_jsp", "../booking/seat.jsp");
		return "main/main";
	}

}
