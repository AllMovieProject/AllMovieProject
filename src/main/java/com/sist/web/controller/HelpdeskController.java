package com.sist.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.HelpDeskService;
import com.sist.web.vo.HelpDeskVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/helpdesk")
public class HelpdeskController {
	
	private final HelpDeskService hService;
	
	@GetMapping("/list")
	public String helpdesk_list(@RequestParam(name = "page", required = false)String page, Model model) {
		if(page == null)
			page = "1";
		int curpage = Integer.parseInt(page);
		
		List<HelpDeskVO> list = hService.helpDeskListData((curpage-1)*12);
		int totalpage = hService.helpDeskTotalPage();
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				
		model.addAttribute("main_jsp", "../helpdesk/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/detail")
	public String helpdesk_detail(@RequestParam("hno") int hno, Model model) {
		HelpDeskVO vo = hService.helpDeskDetailData(hno);
		
		model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../helpdesk/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/insert")
	public String helpdesk_insert(Model model) {
		System.out.println("helpdesk_insert");
		String cateGroup = "HELP1";
		List<HelpDeskVO> cateGroup1 = hService.helpDeskCateData(cateGroup);
		
		cateGroup = "HELP2";
		List<HelpDeskVO> cateGroup2 = hService.helpDeskCateData(cateGroup);
		
		model.addAttribute("cate1List", cateGroup1);
		model.addAttribute("cate2List", cateGroup2);
		model.addAttribute("main_jsp", "../helpdesk/insert.jsp");
		return "main/main";
	}
	
	@PostMapping("/insert_ok")
	public String helpdesk_insert_ok(@ModelAttribute HelpDeskVO vo) {
		vo.setId("userName");
		hService.helpDeskInsert(vo);
		return "redirect:/helpdesk/list";
	}
	
	@GetMapping("/update")
	public String helpdesk_update(@RequestParam("hno") int hno, Model model) {

	    // ❗ 조회수 증가 없는 데이터 조회
	    HelpDeskVO vo = hService.helpdeskUpdateData(hno);

	    List<HelpDeskVO> cate1List = hService.helpDeskCateData("HELP1");
	    List<HelpDeskVO> cate2List = hService.helpDeskCateData("HELP2");

	    model.addAttribute("vo", vo);
	    model.addAttribute("cate1List", cate1List);
	    model.addAttribute("cate2List", cate2List);

	    model.addAttribute("main_jsp", "../helpdesk/update.jsp");
	    return "main/main";
	}
	
	@PostMapping("/update_ok")
	public String helpdesk_update_ok(@ModelAttribute HelpDeskVO vo) {
	    hService.helpdeskUpdate(vo);
	    return "redirect:/helpdesk/detail?hno=" + vo.getHno();
	}	
	
	@GetMapping("/delete")
	public String helpdesk_delete(@RequestParam("hno") int hno, Model model) {
	    model.addAttribute("hno", hno);
	    return "helpdesk/delete"; 
	}

	@PostMapping("/delete_ok")
	public String helpdesk_delete_ok(@RequestParam("hno") int hno) {
	    hService.helpdeskDelete(hno);
	    return "redirect:/helpdesk/list";
	}
	
}
