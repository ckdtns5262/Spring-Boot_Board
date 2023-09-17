package com.example.board.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.example.board.model.board.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BoardRepository {
	private Map<Long, Board> boards = new HashMap<>(); // 키값으로 id 
	
	public BoardRepository() {
		log.info("BoardRepository 생성");
	}
	
	private Long sequence = 0L;
	
	// 게시글 등록 
	public void saveBoard(Board board) {
		board.setId(++sequence);
		boards.put(board.getId(), board);
	}
	// 게시글 검색 
	public Board findBoard(Long id) {
		Board board = boards.get(id);
		return board;
	}
	// 게시글 수정 
	public void updateBoard(Long id, Board updateboard) {
		Board board = boards.get(id);
		if(board != null) {
			board.setTitle(updateboard.getTitle());
			board.setContents(updateboard.getContents());
			board.setUsername(updateboard.getUsername());
			Board put = boards.put(id, board);
			System.out.println("업데이트 된 게시글 : " + put);
		}
	}
	// 게시글 삭제 
	public void removeBoard(Long id) {
		
		boards.remove(id);
	}
	// 게시글 전체 목록
	public List<Board> findAllBoard(){
		List<Board> list = new ArrayList<>();
		list.addAll(boards.values());
		return list;
	
	}
}
