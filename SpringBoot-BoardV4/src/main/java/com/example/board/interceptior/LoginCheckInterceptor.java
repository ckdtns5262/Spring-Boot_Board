package com.example.board.interceptior;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();
		
		if (session == null || session.getAttribute("loginMember") == null) {
			log.info("로그인을 하지 않은 사용자의 요청");
			response.sendRedirect("/member/login?redirectURL=" + requestURI);
			return false;
		}

		return true; // 로그인 정보가 있을때 controller로 넘어가게끔
	}

}
