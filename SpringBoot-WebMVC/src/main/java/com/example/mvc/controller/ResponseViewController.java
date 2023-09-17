package com.example.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

	private final Logger log = LoggerFactory.getLogger(ResponseViewController.class);
	
	/**
	 * ModelAndView를 사용해서 뷰 템플릿 찾기
	 */
	
	@GetMapping("response-view")
	public ModelAndView responseView() {
		// data라는 이름을 가진 변수를 "Hello World"를 붙여서 view 생성
		ModelAndView view = new ModelAndView("response/hello").addObject("data","Hello World");
		return view;
	}
	
	/**
	 * ViewResolver를 통해 뷰 템플릿 찾기 
	 */
	
	@GetMapping("response-view-v2")
	public String responseViewV2(Model model) {
		model.addAttribute("data", "Hello World2");
		return "response/hello";
	}
	/**
	 * return타입을 void로 지정하면 url 요청 
	 */
	
	@GetMapping("response/hello")
	public void responseViewV3(Model model) {
		model.addAttribute("data", "Hello World3");
	}
}
