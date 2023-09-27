package com.example.board.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.member.LoginForm;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
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
		model.addAttribute("member", new MemberJoinForm());
		return "member/joinForm";
	}
	
//	@Transactional(readOnly = true)
	@PostMapping("join")
	public String join(@Validated @ModelAttribute("member") MemberJoinForm memberJoinForm, 
						BindingResult result, Model model) {
		// 회원가입 처리
		log.info("member : {}", memberJoinForm);
		log.info("result : {}", result);
		
		// Validation 유효성체크
		if(result.hasErrors()) {
			return "/member/joinForm";
		}
		Member member = MemberJoinForm.toMember(memberJoinForm);
		
		// 이메일 주소검증
		if(!memberJoinForm.getEmail().contains("@")) {
//			model.addAttribute("errMsg", "메일 주소에 @를 넣어야함");
			result.reject("emailError" , "이메일 형식이 맞지 않습니다.");
			return "/member/joinForm";
		}
		
		// 이미 가입된 아이디가 있는지
		Member findMember = memberMapper.findMember(memberJoinForm.getMember_id());
		if(findMember != null) {
			result.reject("idError", "이미 가입된 ID 입니다 ");
			return "/member/joinForm";
		} else {
			memberMapper.saveMember(member);
			return "redirect:/";
		}
		
		
//		if(findMember == null) {
//			memberMapper.saveMember(member);
//			return "redirect:/";
//		} else {
//			result.reject("idError", "이미 가입된 ID 입니다");
//			return "/member/joinForm";
//		}
		
		
		
//		throw new NullPointerException();
		
	}
	
	
	@GetMapping("login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "member/loginForm";
	}
	
	@PostMapping("login")
	public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
						BindingResult result, Model model, HttpServletResponse response, HttpServletRequest request) {
		
		log.info("loginForm : {}", loginForm);
		if(result.hasErrors()) {
			return "member/loginForm";
		}
		
		// 로그인 검증
		Member findMember = memberMapper.findMember(loginForm.getMember_id());
		log.info("findMember : {}", findMember);
		if(findMember == null) {
			result.reject("loginError" , "아이디가 존재하지 않습니다");
			return "member/loginForm";
		}
		else if(findMember != null && !findMember.getPassword().equals(loginForm.getPassword())) {
			result.reject("loginError1", "비밀번호가 일치하지 않습니다");
			return "member/loginForm";
		}
		
		// 로그인 처리
		// 쿠키를 이용한 로그인처리 (선호 X) 
		// 쿠키 : 웹브라우저와 서버의 도메인 사이에 생성된 데이터로 클라이언트 사이드에 저장
//		Cookie cookie = new Cookie("cookieLoginId", findMember.getMember_id());
		// 쿠키는 도메인의 디렉토리별로 저장되기 떄문에 path를 /로 지정하여 모든 경로에서 읽을 수 있게 처리
//		cookie.setPath("/");
//		response.addCookie(cookie);
		
		
		// 세션을 이용한 로그인처리 (선호 O) 
		// 세션 : 웹브라우저와 서버 사이에 생성된 데이터로 서버 사이드에 저장
		// request 객체에서 세션을 받아온다.
		HttpSession session = request.getSession();
		// session 저장 
		session.setAttribute("loginMember", findMember);
		return "redirect:/";
	}
	
	
	@GetMapping("sessionInfo")
	public String sessionInfo(HttpServletRequest request) {
		// getSession()는 비워놓으면 session을 만듬. false : session이 없으면 null을 return
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "redirect:/member/login";
		}
		log.info("sessionId : {} " , session.getId());
		log.info("maxInactiveInterval : {} " , session.getMaxInactiveInterval());
		log.info("getCreationTime : {} " , new Date(session.getCreationTime()));
		log.info("getLastAccessedTime : {} " , new Date(session.getLastAccessedTime()));
		
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpServletResponse response, HttpServletRequest request) {
		// 전과 같은 이름을 만들면서 값을 null로 
//		Cookie cookie = new Cookie("cookieLoginId", null);
//		cookie.setPath("/");
//		cookie.setMaxAge(0);  // 쿠키 유지시간
//		response.addCookie(cookie);
		
		// 세션초기화 첫번째 방법
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", null);
		
		// 세션초기화 두번째 방법
		session.invalidate();
		
		
		return "redirect:/";
	}
	
}
