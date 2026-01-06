package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpdeskController {
	@GetMapping("/helpdesk/list")
	public String helpdesk_list(Model model) {
		model.addAttribute("main_jsp", "../helpdesk/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/helpdesk/detail")
	public String helpdesk_detail(Model model) {
		model.addAttribute("main_jsp", "../helpdesk/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/helpdesk/insert")
	public String helpdesk_insert(Model model) {
		model.addAttribute("main_jsp", "../helpdesk/insert.jsp");
		return "main/main";
	}
	
	@GetMapping("/helpdesk/update")
	public String helpdesk_update(Model model) {
		model.addAttribute("main_jsp", "../helpdesk/update.jsp");
		return "main/main";
	}
	
	@GetMapping("/helpdesk/delete")
	public String helpdesk_delete(Model model) {
		model.addAttribute("main_jsp", "../helpdesk/delete.jsp");
		return "main/main";
	}
}
