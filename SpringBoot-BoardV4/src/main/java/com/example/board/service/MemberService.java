package com.example.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.model.member.Member;
import com.example.board.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	
	private final MemberMapper memberMapper;
	
	@Transactional
	public void saveMember(Member member) {
		// 이미 가입된 아이디가 있는지
		memberMapper.saveMember(member);
		
	}
	
	public Member findMember(String member_id) {
		return memberMapper.findMember(member_id);
		
		
	}

}
