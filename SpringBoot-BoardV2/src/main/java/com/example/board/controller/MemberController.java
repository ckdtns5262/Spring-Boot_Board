package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.member.Member;
import com.example.board.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	private MemberMapper memberMapper;
	
//	@Autowired
//	public void setMemberMapper(MemberMapper memberMapper) {
//		this.memberMapper = memberMapper;
//	}
	
	// 회원가입 페이지 이동 
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("member", new Member());
		return "member/joinForm";
	}
	
	@Transactional(readOnly = true)
	@PostMapping("join")
	public String join(@ModelAttribute Member member) {
		// 회원가입 처리
		memberMapper.saveMember(member);
		log.info("member : {}", member);
//		throw new NullPointerException();
		return "redirect:/";
	}
}
