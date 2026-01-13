package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.BoardService;
import com.sist.web.vo.BoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService bService;
	
	@GetMapping("/board/list")
	public String board_list(@RequestParam(name = "page", required = false)String page, Model model) {
		if(page == null)
			page = "1";
		int curpage = Integer.parseInt(page);
		
		List<BoardVO> list = bService.boardListData((curpage-1)*12);
		int totalpage = bService.boardTotalPage();
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("today", new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
				
		model.addAttribute("main_jsp", "../board/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/detail")
	public String board_detail(Model model) {
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
