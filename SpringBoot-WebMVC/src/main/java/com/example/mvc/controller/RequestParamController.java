package com.example.mvc.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mvc.model.MemberData;

@Controller	
public class RequestParamController {
	
	private final Logger log = LoggerFactory.getLogger(RequestParamController.class);
	
	@ResponseBody // @ResponseBody를 해주면 index만 return 해줌(RestController를 해준것과 동일함)
	@RequestMapping("request-param")
	public String requestParam(HttpServletRequest request) {
		log.info("request-param 실행");
		log.info("httpServlet 값 : {}", request);
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		log.info("id: {} ,", id);
		log.info("name: {}, " , name);
		return "index";
	}
	/**
	 * @RequestParam으로 요청 파라미터를 읽을 수 있다.
	 * 요청 파라미터의 key와 메소드의 변수명이 다르면
	 * value or name 속성을 지정하여 변수명을 동일하게 해줘야 한다. 
	 * 요청 파라미터를 꼭 받지 않아도 될 경우 required 속성을 false로(기본값은 true)
	 * 요청 파라미터의 값이 없을 경우 defaultValue(기본값)을 설정할 수 있다.
	 * 
	 * @param id : 회원의 id
	 * @param name : 회원의 이름
	 * @return : 텍스트
	 */
	@ResponseBody
	@RequestMapping("request-param-v2")
	public String requestParamV2(@RequestParam("id") String id,
								 @RequestParam(value="name", required=false) String name,
								 @RequestParam(defaultValue = "40") String age) {
		log.info("request-param-v2 실행");
		log.info("id {}" , id);
		log.info("name: {}" , name);
		log.info("age: {}" , age);
		return "ok";
	}
	/**
	 * @RequestParam 생략가능 
	 * 요청 파라미터가 없으면 각 데이터타입의 기본값으로 받는다. (ex : String일 경우 null)
	 */
	@ResponseBody
	@RequestMapping("request-param-v3")
	public String requestParamV3(String id, String name) {
		log.info("request-param-v3 실행");
		log.info("id {}" , id);
		log.info("name: {}" , name);
		return "ok!!";
	}
	
	/**
	 * 파라미터 타입을 지정하지 않고 Map으로 한번에 받을 수 있다.
	 * 
	 */
	@ResponseBody
	@RequestMapping("request-param-map")
	public String requestParamV4(@RequestParam Map<String, String> paramMap) {
		log.info("request-param-map 실행");
		log.info("paramMap: {}" , paramMap);
		return "ok4";
	}
	
	/**
	 * @ModelAttribute 어노테이션을 통한 파라미터 바인딩 
	 * 바인딩 순서 
	 * 1. MemberData 객체를 생성 
	 * 2. 요청 파라미터(key)의 이름과 MemberData 클래스의 필드(멤버변수)가 같은것을 찾고 
	 * 3. 요청 파라미터와 MemberData 객체의 데이터 타입이 다르면 BindingException
	 * ex) int타입인데 숫자로 변환불가 문자열 보낸경우
	 * 4. @ModelAttribute 생략 가능 
	 */
	
	@ResponseBody
	@RequestMapping("modelAttribute-v1")
	public String modelAttributeV1(@ModelAttribute MemberData member) {
		log.info("member: {}", member);
		return "ok";
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	
}
