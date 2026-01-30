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
        model.addAttribute("main_jsp", "../booking/bookingMain.jsp");
        return "main/main";
    }
}
