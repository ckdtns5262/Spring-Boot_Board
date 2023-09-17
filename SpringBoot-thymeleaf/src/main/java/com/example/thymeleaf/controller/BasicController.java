package com.example.thymeleaf.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.thymeleaf.model.Member;

@RequestMapping("basic") //부모경로 : url을 통일시켜서 중복막고 큰 카테고리로 묶기 위해서 사용
@Controller
public class BasicController {

	@GetMapping("text-basic")
	public String textBasic(Model model) {
		model.addAttribute("data", "Hello Spring");
		return "basic/text-basic";
	}
	
	@GetMapping("text-unescaped")
	public String textUnescaped(Model model) {
		model.addAttribute("data", "Hello <b>Spring</b>");
		return "basic/text-unescaped";
	}
	
	@GetMapping("variable")
	public String variable(Model model) {
		Member member1 = new Member("1", "홍길동", 30);
		Member member2 = new Member("2", "홍이동", 20);
		List<Member> list = new ArrayList<>();
		list.add(member1);
		list.add(member2);
		
		Map<String,Member> memberMap = new HashMap<>();
		memberMap.put("member1", member1);
		memberMap.put("member2", member2);
		
		model.addAttribute("member", member1);
		model.addAttribute("members", list);
		model.addAttribute("memberMap", memberMap);
		
		return "basic/variable";
	}
	
//	@GetMapping("basic-objects")
//	public String basicObjects(Model model) {
//		return "basic/basic-objects";
//	}
//	
	@GetMapping("basic-objects")
	public String basicObjects2(HttpSession session) {
		session.setAttribute("sessionData", "Hello Spring");
		return "basic/basic-objects";
	}
	
	@GetMapping("date")
	public String date(Model model) {
		model.addAttribute("localDateTime", LocalDateTime.now());
		return "basic/date";
	}
	
	@GetMapping("link")
	public String link(Model model) {
		model.addAttribute("param1", "data1");
		model.addAttribute("param2", "data2");
		return "basic/link";
	}
	
	@GetMapping("literal")
	public String literal(Model model) {
		model.addAttribute("data", "Spring");
		return "basic/literal";
	}
	
	@GetMapping("operation")
	public String operation(Model model) {
		model.addAttribute("data", "Hello Spring");
		model.addAttribute("nullData", null);		
		return "basic/operation";
	}
	
	@GetMapping("attribute")
	public String attribute(Model model) {
		return "basic/attribute";
	}
	
	@GetMapping("each")
	public String each(Model model) {
		addMembers(model);
		return "basic/each";
	}
	
	@GetMapping("condition")
	public String condition(Model model) {
		addMembers(model);
		return "basic/condition";
	}
	
	@GetMapping("comments")
	public String comments(Model model) {
		model.addAttribute("data", "Spring");
		return "basic/comments";
	}
	
	@GetMapping("block")
	public String block(Model model) {
		addMembers(model);
		return "basic/block";
	}
	
	@GetMapping("javascript")
	public String javaScript(Model model) {
		model.addAttribute("member", new Member("hong", "홍길동", 20));
		addMembers(model);
		return "basic/javascript";
	}
	
	
	private void addMembers(Model model) {
		List<Member> members = new ArrayList<>();
		members.add(new Member("hong" , "홍길동", 20));
		members.add(new Member("lee" , "이길동", 30));
		members.add(new Member("kim" , "김길동", 40));
		model.addAttribute("members", members);
	}
	
	
	
}
