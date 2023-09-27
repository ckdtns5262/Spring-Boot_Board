package com.example.board.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.board.model.board.filter.LogFilter;
import com.example.board.model.board.filter.LoginCheckFilter;

@Configuration
public class WebConfig {
	
	@Bean
	public FilterRegistrationBean<Filter> logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		
		// 등록할 필터를 지정
		filterRegistrationBean.setFilter(new LogFilter());
		// 필터의 순서적용, 숫자가 낮을수록 먼저 실행
		filterRegistrationBean.setOrder(1);
		// 필터를 적용할 URL 패턴을 지정
		filterRegistrationBean.addUrlPatterns("/*"); // 최상위경로 밑으로 들어오는 모든 경로에 필터를 적용
		
		return filterRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> loginCheckFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		
		filterRegistrationBean.setFilter(new LoginCheckFilter());
		
		filterRegistrationBean.setOrder(2);
		
		filterRegistrationBean.addUrlPatterns("/*");
		
		return filterRegistrationBean;
	}
	
	
	
	
	
	
}
