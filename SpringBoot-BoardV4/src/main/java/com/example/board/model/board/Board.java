package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {

	private Long board_id; // 게시물 아이디 
	private String title; // 게시글 제목 
	private String contents; // 게시글 내용 
	private String member_id; // 작성자 
	private Long hit; // 조회수 
	private LocalDateTime created_time; // 작성일 
	
//	public Board(String title, String contents, String member_id, String password) {
//		this.title = title;
//		this.contents = contents;
//		this.member_id = member_id;
//		this.hit = 0L;
//		this.created_time = LocalDateTime.now(); // 그때 작성한 시간을 반환
//	}
	
	public void addHit() {
		// this.hit = hit + 1; 
		this.hit++;
	}
	
	public static BoardUpdateForm toBoardUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		boardUpdateForm.setBoard_id(board.getBoard_id());
		boardUpdateForm.setTitle(board.getTitle());
		boardUpdateForm.setContents(board.getContents());
		boardUpdateForm.setMember_id(board.getMember_id());
		boardUpdateForm.setHit(board.getHit());
		boardUpdateForm.setCreated_time(board.getCreated_time());
		
		return boardUpdateForm;
	}
}
