package com.sist.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.web.service.MypageService;
import com.sist.web.vo.ScheduleVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MypageService mService;
	
	@GetMapping("/mypage")
	public String mypage_main(Model model) {
		model.addAttribute("mypage_jsp", "../mypage/userinfo.jsp");
		model.addAttribute("main_jsp", "../mypage/mypage_main.jsp");
		return "main/main";
	}

	@GetMapping("/mypage/bookinglist")
	public String mypage_bookinglist(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		List<ScheduleVO> list = mService.bookingListData(id);
		
		model.addAttribute("booking_list", list);
		
        model.addAttribute("mypage_jsp", "../mypage/bookinglist.jsp");
        model.addAttribute("main_jsp", "../mypage/mypage_main.jsp");
		return "main/main";
	}
}
