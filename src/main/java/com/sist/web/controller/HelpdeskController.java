package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.HelpDeskService;
import com.sist.web.vo.BoardVO;
import com.sist.web.vo.HelpDeskVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HelpdeskController {
	private final HelpDeskService hService;
	
	@GetMapping("/helpdesk/list")
	public String helpdesk_list(@RequestParam(name = "page", required = false)String page, Model model) {
		if(page == null)
			page = "1";
		int curpage = Integer.parseInt(page);
		
		List<HelpDeskVO> list = hService.helpDeskListData((curpage-1)*12);
		int totalpage = hService.helpDeskTotalPage();
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("today", new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
				
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
