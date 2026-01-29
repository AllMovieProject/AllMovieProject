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

import com.sist.web.service.GroupVisitService;
import com.sist.web.vo.GroupVisitVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groupvisit")
public class GroupvisitController {
	private final GroupVisitService gService;
	
	@GetMapping("/list")
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
	
	@GetMapping("/detail")
	public String groupvisit_detail(@RequestParam("gno")int gno, Model model) {
		GroupVisitVO vo = gService.groupVisitDetailData(gno);
		
		model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../groupvisit/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/insert")
	public String groupvisit_insert(Model model) {
		String cateGroup = "GROUPVISIT";
		List<GroupVisitVO> cateGroup1 = gService.groupVisitCateData(cateGroup);
		
		model.addAttribute("cate1List", cateGroup1);		
		model.addAttribute("main_jsp", "../groupvisit/insert.jsp");
		return "main/main";
	}
	
	@PostMapping("/insert_ok")
	public String groupvisit_insert_ok(@ModelAttribute GroupVisitVO vo) {
		vo.setId("userName");
		gService.groupVisitInsert(vo);
		return "redirect:/groupvisit/list";
	}
	
	@GetMapping("/update")
	public String groupvisit_update(@RequestParam("gno") int gno,  Model model) {
		GroupVisitVO vo = gService.groupVisitDetailData(gno);
		
		List<GroupVisitVO> cate1List = gService.groupVisitCateData("GROUPVISIT");
		
		model.addAttribute("vo", vo);
		model.addAttribute("cate1List", cate1List);
		model.addAttribute("main_jsp", "../groupvisit/update.jsp");
		return "main/main";
	}
	
	@PostMapping("/update_ok")
	public String groupvisit_update_ok(@ModelAttribute GroupVisitVO vo) {
		gService.groupVisitUpdate(vo);
		return "redirect:/groupvisit/detail?gno=" + vo.getGno();
	}
	
	@GetMapping("/delete")
	public String groupvisit_delete(@RequestParam("gno")int gno, Model model) {
		model.addAttribute("gno", gno);
		return "groupvisit/delete";
	}
	
	@PostMapping("/delete_ok")
	public String groupvisit_delete_ok(@RequestParam("gno")int gno) {
		gService.groupVisitDelete(gno);
		return "redirect:/groupvisit/list";
	}
	
}
