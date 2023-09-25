package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	// 메인페이지 이동
	@GetMapping("/")
	// required 속성을 주면 쿠키가 있을때 없을 때 나눠서 가능함
	public String home(@CookieValue(name = "cookieLoginId", required = false) String cookieLoginId, 
			Model model) {
		log.info("home() 실행");
//		log.info("cookieLoginId : {}" , cookieLoginId);
//		model.addAttribute("cookieLoginId", cookieLoginId);
		return "index";
	}
	
	

}
