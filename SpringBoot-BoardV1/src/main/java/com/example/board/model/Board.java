package com.example.board.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {

	private Long id; // 게시물 아이디 
	private String title; // 게시글 제목 
	private String contents; // 게시글 내용 
	private String username; // 작성자 
	private String password; // 패스워드 
	private Long hit; // 조회수 
	private LocalDateTime created_time; // 작성일 
	
	public Board(String title, String contents, String username, String password) {
		this.title = title;
		this.contents = contents;
		this.username = username;
		this.password = password;
		this.hit = 0L;
		this.created_time = LocalDateTime.now(); // 그때 작성한 시간을 반환
	}
	
	public void addHit() {
		// this.hit = hit + 1; 
		this.hit++;
	}
}
