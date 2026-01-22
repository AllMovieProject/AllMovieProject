package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
	@GetMapping("/mypage")
	public String mypage_main(Model model) {
		model.addAttribute("mypage_jsp", "../mypage/userinfo.jsp");
		model.addAttribute("main_jsp", "../mypage/mypage_main.jsp");
		return "main/main";
	}

	@GetMapping("/mypage/bookinglist")
	public String mypage_bookinglist(Model model) {
		model.addAttribute("mypage_jsp", "../mypage/bookinglist.jsp");
		model.addAttribute("main_jsp", "../mypage/mypage_main.jsp");
		return "main/main";
	}
}
