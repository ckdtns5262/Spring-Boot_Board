package com.example.board.interceptior;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

	@Override // 요청이 들어왔을 때 Controller 들어가기전에 확인 가능
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI(); // URI가 URL을 포함하고 있음
		log.info("preHandle() 실행 : {} " , requestURI);
		
		
		
		return true;
	}
	
	@Override // 응답에 대한 정보중에서 model과 view 정보를 볼수 있음
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			// 컨트롤러에서 예외가 발생하면 postHandle은 호출되지 않는다.
			log.info("postHandle() 실행 ");
			log.info("modelAndView : {}" , modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 컨트롤러에서 예외발생과 관계없이 호출된다.
		log.info("afterCompletion() 실행");
	}
	
}
