package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sist.web.dto.HomeInfoDTO;
import com.sist.web.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService mService;

	@GetMapping("/")
	public String main_page(Model model) {
		HomeInfoDTO dto = mService.homeListData();
		model.addAttribute("data", dto);
		model.addAttribute("main_jsp", "../main/home.jsp");
		return "main/main";
	}
	
}
