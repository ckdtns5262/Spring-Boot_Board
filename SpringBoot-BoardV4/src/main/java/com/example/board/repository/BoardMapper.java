package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;

@Mapper
public interface BoardMapper {
	
	void saveBoard(Board board);
	
	Board findBoardById(Long board_id);
	
	List<Board> findAllBoard();
	
	void updateBoard(Board updateBoard);
	
	void removeBoard(Long board_id);
	
	void saveFile(AttachedFile attachedFile);

}
