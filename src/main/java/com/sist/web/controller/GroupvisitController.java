package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.GroupVisitService;
import com.sist.web.vo.GroupVisitVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GroupvisitController {
	private final GroupVisitService gService;
	
	@GetMapping("/groupvisit/list")
	public String groupvisit_list(@RequestParam(name = "page", required = false) String page,  Model model) {
		if(page == null)
			page = "1";
		int curpage = Integer.parseInt(page);
		
		List<GroupVisitVO> list = gService.GroupVisitListData((curpage-1)*12);
		int totalpage = gService.GroupVisitTotalPage();
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
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
