package com.example.mvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.model.MemberData;

@RestController
public class ResponseBodyController {

	private final Logger log = LoggerFactory.getLogger(ResponseBodyController.class);
	
	@GetMapping("responseBody-v1") // api 응답방식으로 호출해주는게 ResponseEntity
	public ResponseEntity<String> responseBodyV1(){
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	
	@GetMapping("responseBody-v2")
	public String responseBodyV2() {
		return "ok";
	}
	
	@GetMapping("responseBody-v3")
	public ResponseEntity<MemberData> responseBodyV3() {
		MemberData data = new MemberData();
		data.setId("hong");
		data.setName("홍길동");
		data.setAge(30);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}
	
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) 어노테이션으로도 가능
	@GetMapping("responseBody-v4")
	public MemberData responseBodyV4(){
		MemberData data = new MemberData();
		data.setId("kim");
		data.setName("김철수");
		data.setAge(40);
		return data;
	}
	
}
