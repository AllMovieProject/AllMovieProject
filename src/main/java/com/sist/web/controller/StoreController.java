package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class StoreController {
	
	@GetMapping("/list")
	public String store_list(Model model) {
		model.addAttribute("main_jsp", "../store/list.jsp");
		return "main/main";
	}
	
	@GetMapping("/detail")
	public String store_detail(@RequestParam("store_id") int store_id, @RequestParam("product_id") int product_id, Model model, HttpSession session) {
		model.addAttribute("userid", session.getAttribute("userid"));
		model.addAttribute("main_jsp", "../store/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("/stock")
	public String store_stock(Model model) {
		model.addAttribute("main_jsp", "../store/stock.jsp");
		return "main/main";
	}
	
	@GetMapping("/product_insert")
	public String store_product_insert(Model model) {
		model.addAttribute("main_jsp", "../store/product_insert.jsp");
		return "main/main";
	}
	
	@GetMapping("/cart")
	public String store_cart(Model model) {
		model.addAttribute("main_jsp", "../store/cart.jsp");
		return "main/main";
	}
	
	@GetMapping("/payment")
	public String store_payment(Model model) {
		model.addAttribute("main_jsp", "../store/payment.jsp");
		return "main/main";
	}
	
	@GetMapping("/manager/order")
	public String store_manager_order(Model model) {
		model.addAttribute("main_jsp", "../store/order_manage.jsp");
		return "main/main";
	}

}
