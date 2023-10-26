package com.example.board.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.model.member.Member;
import com.example.board.model.member.RoleType;
import com.example.board.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	
	@Transactional
	public void saveMember(Member member) {
		// 이미 가입된 아이디가 있는지
		String rawPassword = member.getPassword();
		// 비크립트 방식으로 암호화
		String encode = passwordEncoder.encode(rawPassword);
	
		member.setPassword(encode);
		
		log.info("암호화비번 : {}", encode);
		
		// 기본 ROLE 부여 
		member.setRole(RoleType.ROLE_USER);
		
		memberMapper.saveMember(member);
		
	}
	
	public Member findMember(String member_id) {
		return memberMapper.findMember(member_id);
		
		
	}

}
