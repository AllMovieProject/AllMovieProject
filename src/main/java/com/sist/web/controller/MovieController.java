package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.MovieService;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
	
	private final MovieService mService;

	@GetMapping("/detail")
	public String movie_detail(@RequestParam("movieId") int movieId, Model model) {
		MovieVO vo = mService.movieDetailData(movieId);
		model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../movie/detail.jsp");
		return "main/main";
	}
}
