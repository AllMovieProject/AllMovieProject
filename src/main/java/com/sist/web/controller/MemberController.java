package com.sist.web.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.web.service.MemberService;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final BCryptPasswordEncoder encoder;
	private final MemberService mService;

	@GetMapping("/member/join")
	public String member_join(Model model) {
		model.addAttribute("main_jsp", "../member/join.jsp");
		return "main/main";
	}
	
	@PostMapping("/member/join_ok")
	public String member_join_ok(@ModelAttribute("vo") MemberVO vo) {
		vo.setUserpwd(encoder.encode(vo.getUserpwd()));
		mService.memberInsert(vo);
		mService.memberAuthorityInsert(vo.getUserid());
		return "redirect:/";
	}
	
	@RequestMapping("/member/login")
	public String member_login(Model model) {
		model.addAttribute("main_jsp", "../member/login.jsp");
		return "main/main";
	}
}
