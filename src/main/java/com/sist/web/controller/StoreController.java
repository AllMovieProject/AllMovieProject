package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
public class StoreController {
	
	@GetMapping("/list")
	public String store_list(Model model) {
		model.addAttribute("main_jsp", "../store/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/detail")
	public String store_detail(@RequestParam("id") int id, Model model) {
		model.addAttribute("main_jsp", "../store/detail.jsp");
		return "main/main";
	}

}
