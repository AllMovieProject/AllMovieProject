package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.dto.ListDTO;
import com.sist.web.service.MovieService;
import com.sist.web.vo.MovieVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
	
	private final MovieService mService;
	
	@GetMapping("/list")
	public String foodList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		ListDTO<MovieVO> dto = mService.movieListData(page);
		model.addAttribute("data", dto);
		model.addAttribute("main_jsp", "../movie/list.jsp");
		return "main/main";
	}

	@GetMapping("/detail")
	public String movie_detail(@RequestParam("movie-id") int movie_id, Model model) {
		MovieVO vo = mService.movieDetailData(movie_id);
		model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../movie/detail.jsp");
		return "main/main";
	}
	
}
