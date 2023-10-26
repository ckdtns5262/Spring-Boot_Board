package com.example.board.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/*
 	로그인 폼에서 아이디와 패스워드를 입력하고 로그인 요청을 하면 UserDetailsService의 
 	loadUserByUserName 메서드를 자동으로 호출한다.
 */

@Service
@Slf4j
public class UserService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername : {}",username);
		return null;
	}

}
