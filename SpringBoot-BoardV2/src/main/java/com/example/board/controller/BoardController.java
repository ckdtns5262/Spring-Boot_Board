package com.example.board.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.board.Board;
import com.example.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 초기데이터 생성(생성 이후에 실행될 메서드)
	@PostConstruct
	public void initData() {
		boardRepository.saveBoard(new Board("제목1", "내용1", "작성자1","1234"));
		boardRepository.saveBoard(new Board("제목2", "내용2", "작성자2","5678"));
		boardRepository.saveBoard(new Board("제목3", "내용3", "작성자3","4321"));
		boardRepository.saveBoard(new Board("제목4", "내용4", "작성자4","8765"));
	}
		
	// 메인페이지 이동 
	@GetMapping("")
	public String home() {
		log.info("home() 실행");
		return "redirect:/list";
	}
	
	// 게시글 작성 페이지로 이동 
	@GetMapping("write")
	public String writeForm() {
		return "write";
	}
	
	// 게시글 저장
	@PostMapping("write")
	public String write(@ModelAttribute Board board) {
		log.info("board: {}" + board);
		boardRepository.saveBoard(board);
		return "/";
	}
	
	// 게시글 목록 출력 
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("boards", boardRepository.findAllBoard());
		return "list";
	}
	
	// 게시글 읽기 
	@GetMapping("read")
	public String read(@RequestParam Long id, Model model) {
		Board board = boardRepository.findBoard(id);
	//	board.setHit(board.getHit() + 1);
		board.addHit();
		model.addAttribute("board", board);
		return "read";
	}
	
	// 수정하기 페이지 이동
	@GetMapping("update")
	public String update(@RequestParam Long id, Model model) {
		model.addAttribute("board", boardRepository.findBoard(id));
		return "update";	
	}
	
	// 게시글 수정
	@PostMapping("update")
	public String updateBoard(@RequestParam Long id, @ModelAttribute Board updateBoard, Model model) {
		log.info("id : {} , board : {} " , id, updateBoard);
		Board board = boardRepository.findBoard(id);
		if(board.getPassword().equals(updateBoard.getPassword())) {
			boardRepository.updateBoard(id, updateBoard);
		}
		return "redirect:/";
		
		// 수정작업 
		// 1. 제목, 내용, 작성자 수정되도록 
		// 2. 글 작성시 썻던 패스워드와 맞아야 함
		// 3. 작성자 입력칸 
		// 4. 수정하기 페이지를 로드했을 때 전에 썼던 내용이 표시되도록.
	}
	
	// 게시글 삭제
	// 처음 작성할 때 만들었던 패스워드를 입력하고 삭제하기 버튼을 누르면 삭제되도록
	@PostMapping("delete")
	public String remove(@RequestParam Long id, @RequestParam String password) {
		// id에 해당하는 게시글이 있고, 입력한 패스워드가 일치하면 게시글을 삭제
		Board findBoard = boardRepository.findBoard(id);
		log.info("id : {}, password : {}" , id , password);
		if(findBoard != null && password.equals(findBoard.getPassword())) {
			boardRepository.removeBoard(id);
		}
		return "redirect:/";
	}
}
