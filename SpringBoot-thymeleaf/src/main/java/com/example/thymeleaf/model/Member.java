package com.example.thymeleaf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor // 기본생성자 
@AllArgsConstructor // 필드전체를 받아오는 생성자
public class Member {
	private String id;
	private String name;
	private int age;
	
	
}
