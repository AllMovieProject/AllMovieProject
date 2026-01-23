package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.service.BoardService;
import com.sist.web.vo.BoardVO;
import com.sist.web.vo.HelpDeskVO;

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
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				
		model.addAttribute("main_jsp", "../board/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/detail")
	public String board_detail(@RequestParam("bno") int bno, Model model) {
		BoardVO vo = bService.boardDetailData(bno);
		
		model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../board/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/board/insert")
	public String board_insert(Model model) {
		System.out.println("board_insert");
		String cateGroup = "BOARD";
		List<BoardVO> cateGroup1 = bService.boardCateData(cateGroup);		
		
		model.addAttribute("cate1List", cateGroup1);		
		model.addAttribute("main_jsp", "../board/insert.jsp");
		return "main/main";
	}
	
	@PostMapping("/board/insert_ok")
	public String board_insert_ok(@ModelAttribute BoardVO vo) {
		vo.setId("admin");
		bService.boardInsert(vo);
		return "redirect:/board/list";
	}
	
	@GetMapping("/board/update")
	public String board_update(@RequestParam("bno") int bno, Model model) {
	    BoardVO vo = bService.boardUpdateData(bno);
	    
	    String cateGroup = "BOARD";
	    List<BoardVO> cateList = bService.boardCateData(cateGroup);
	    
	    model.addAttribute("vo", vo);
	    model.addAttribute("cate1List", cateList);
	    model.addAttribute("main_jsp", "../board/update.jsp");
	    return "main/main";
	}
	
	@PostMapping("/board/update_ok")
	public String board_update_ok(@ModelAttribute BoardVO vo) {
	    bService.boardUpdate(vo);
	    return "redirect:/board/detail?bno=" + vo.getBno();
	}
	
	@GetMapping("/board/delete")
	public String board_delete(@RequestParam("bno") int bno, Model model) {
	    model.addAttribute("bno", bno);
	    return "board/delete";
	}
	
	@PostMapping("/board/delete_ok")
	public String board_delete_ok(@RequestParam("bno") int bno) {
	    bService.boardDelete(bno);
	    return "redirect:/board/list";
	}
	
}
