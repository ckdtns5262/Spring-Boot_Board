package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ForwardRedirectController {

	
	@GetMapping("forward")
	public String forward(@RequestParam String param1) {
		log.info("forward param1 :{}" , param1);
		return "forward:/nexturl"; // forward가 붙으면 다음에 붙은 요청값으로 mapping을 시도
	}
	
	@GetMapping("nexturl")
	public String nextUrl(@RequestParam(required = false) String param1, Model model) {
		log.info("nexturl param1 :{}", param1);
		model.addAttribute("param1", param1);
		return "response/next";
	}
	
	@GetMapping("redirect")
	public String redirect(@RequestParam String param1) {
		log.info("redirect param1:{}", param1);
		return "redirect:/nexturl";
	}
}
