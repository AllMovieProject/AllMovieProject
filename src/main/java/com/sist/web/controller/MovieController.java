package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

	@GetMapping("/movie/detail")
	public String movie_detail(Model model) {
		model.addAttribute("main_html", "movie/detail");
		return "main/main";
	}
}
