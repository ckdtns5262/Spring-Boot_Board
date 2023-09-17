package com.example.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.model.MemberData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RequestBodyController {

	private final Logger log = LoggerFactory.getLogger(RequestBodyController.class);
	ObjectMapper objectmapper = new ObjectMapper();
	
	/**
	 * HttpEntity는 헤더 정보를 가지고 있다.
	 * getBody() 메소드를 통해 메시지 바디 내용 읽기
	 * HttpEntity<>를 이용해서 헤더, 바디 정보를 읽어 올수 있다.
	 * 서블릿 방식(자바 웹기술)
	 */
	@PostMapping("request-Json")
	public String requestBodyJson(HttpEntity<String> httpEntity) {
		log.info("httpEntity: {}", httpEntity);
		String body = httpEntity.getBody();
		log.info("body: {}", body);
		return "ok";
	}
	
	/**
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 * @RequestBody 어노테이션을 통해서 body의 정보를 읽기
	 */
	
	@PostMapping("request-Json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonMappingException, JsonProcessingException {
		log.info("messageBody: {}", messageBody);
		MemberData member = objectmapper.readValue(messageBody, MemberData.class);
		log.info("member: {}", member);
		return "ok";
	}
	
	/**
	 * @RequestBody는 객체타입의 파라미터를 받을 수 있다.
	 * @ReuqestBody를 생략하면 @ModelAttribute가 자동으로 붙기 때문에 생략 X
	 */
	
	@PostMapping("request-Json-v3")
	public String requestBodyJsonV3(@RequestBody MemberData member) {
		log.info("member: {}", member);
		return "ok";
	}
	
	// API - 시스템과 시스템의 통신 
	@PostMapping("request-Json-v4")
	public MemberData requestBodyJsonV4(@RequestBody MemberData member) {
		log.info("member: {}", member);
		//member 수정, 추가
		return member;
	}
	
}
