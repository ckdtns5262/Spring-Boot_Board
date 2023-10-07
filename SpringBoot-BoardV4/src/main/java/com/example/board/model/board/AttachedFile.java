package com.example.board.model.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachedFile {

	private Long attachedFile_id;
	private Long board_id;
	private String original_filename;
	private String saved_filename;
	private Long file_size;
	
	public AttachedFile(String original_filename, String saved_filename, Long file_size) {
		this.original_filename = original_filename;
		this.saved_filename = saved_filename;
		this.file_size = file_size;
	}
	
	
}
