package com.example.board.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardUpdateForm {

	
	private Long board_id;
	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	private Long hit;
	private String member_id;
	private LocalDateTime created_time;
	private boolean fileRemoved;
	
}
