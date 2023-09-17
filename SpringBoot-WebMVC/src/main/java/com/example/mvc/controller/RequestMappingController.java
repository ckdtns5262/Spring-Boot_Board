package com.example.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestMappingController {
	
	private final Logger log = LoggerFactory.getLogger(RequestMappingController.class);
	
	
	/*
	 * @RequestMapping은 HTTP 요청 방식을 가리지 않는다.
	 * GET, POST, PUT, PATCH등 모든 방식의 요청을 처리한다.
	 * 요청값은 배열로 받을 수 있다. 
	 * 
	 */
	@RequestMapping({"hello", "hello2"})
	public String hello() {
		return "Hello World";
	}
	
	/**
	 * GET 방식의 요청만 처리 
	 */
	@RequestMapping(value = "hello-get", method = RequestMethod.GET)
	public String helloGet() {
		log.info("hello-get 실행");
		return "Hello Get!";
	}
	
	@GetMapping("hello-get-v2")
	public String helloGetV2() {
		log.info("hello-get-v2 실행");
		return "Hello Get!";
	}
	
	@RequestMapping(value = "hello-post", method = RequestMethod.POST)
	public String helloPost() {
		log.info("hello-post 실행");
		return "Hello Post!";
	}
	@PostMapping("hello-post-v2")
	public String helloPostV2() {
		log.info("hello-post-v2 실행");
		return "Hello Post!";
	}
	
	/**
	 * GET / POST만 받고 싶을때
	 * method(전송방식)도 배열로 넣을수 있다.
	 */
	@RequestMapping(value="hello-get-post", method= {RequestMethod.GET, RequestMethod.POST})
	public String helloGetPost() {
		log.info("hello-get-post 실행");
		return "Hello Get Post";
	}
	
	/**
	 * 경로변수(PathVariable)
	 * ex) localhost:9000/users/{user1}
	 * 전) localhost:9000/users?user_id=user1(GET)
	 */
	@GetMapping("hello/{userId}")
	public String helloPath(@PathVariable("userId") String Id) {
		log.info("helloPath() 실행, Id : {}" , Id);
		return "ok";
	}
	
	
	
}
