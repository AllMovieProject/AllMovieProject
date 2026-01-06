package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping("/board/list")
	public String board_list(Model model) {
		model.addAttribute("main_jsp", "../board/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/detsil")
	public String board_detsil(Model model) {
		model.addAttribute("main_jsp", "../board/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/insert")
	public String board_insert(Model model) {
		model.addAttribute("main_jsp", "../board/insert.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/update")
	public String board_update(Model model) {
		model.addAttribute("main_jsp", "../board/update.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/delete")
	public String board_delete(Model model) {
		model.addAttribute("main_jsp", "../board/delete.jsp");
		return "main/main";
	}
}
