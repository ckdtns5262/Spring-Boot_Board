package com.example.board.model.board;


import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {

	@NotBlank
	private String title; // 게시글 제목 
	@NotBlank
	private String contents; // 게시글 내용 
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setTitle(boardWriteForm.getTitle());
		board.setContents(boardWriteForm.getContents());
		return board;
	}

	
}
