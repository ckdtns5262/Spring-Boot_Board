package com.example.board.model.member;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	
	@Size(min = 4 , max = 20 , message = "아이디는 4~20자 사이 입니다")
	private String member_id;
	@Size(min = 4 , max = 20,  message = "비밀번호는 4~20자 사이 입니다" )
	private String password;

}
