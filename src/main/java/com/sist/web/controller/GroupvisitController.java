package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupvisitController {
	@GetMapping("/groupvisit/list")
	public String groupvisit_list(Model model) {
		model.addAttribute("main_jsp", "../groupvisit/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/groupvisit/detail")
	public String groupvisit_detail(Model model) {
		model.addAttribute("main_jsp", "../groupvisit/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/groupvisit/insert")
	public String groupvisit_insert(Model model) {
		model.addAttribute("main_jsp", "../groupvisit/insert.jsp");
		return "main/main";
	}
	
	@GetMapping("/groupvisit/update")
	public String groupvisit_update(Model model) {
		model.addAttribute("main_jsp", "../groupvisit/update.jsp");
		return "main/main";
	}
	
	@GetMapping("/groupvisit/delete")
	public String groupvisit_delete(Model model) {
		model.addAttribute("main_jsp", "../groupvisit/delete.jsp");
		return "main/main";
	}
}
