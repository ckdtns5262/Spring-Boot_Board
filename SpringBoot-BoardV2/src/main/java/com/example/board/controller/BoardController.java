package com.example.board.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {
	
	@Autowired
	private final BoardMapper boardMapper;
	
	public BoardController(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	
	// 게시글 작성 페이지로 이동 
	@GetMapping("write")
	public String writeForm(@SessionAttribute(value="loginMember", required = false)Member loginMember,
							Model model) {
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		model.addAttribute("writeForm", new BoardWriteForm());
		return "board/write";
	}
	
	// 게시글 저장
	@PostMapping("write")
	public String write(@SessionAttribute(name = "loginMember" ,required = false) Member loginMember,
						@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm, 
						 BindingResult result) {
		log.info("boardWriteForm : {}", boardWriteForm);
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		if(result.hasErrors()) {
			return "board/write";
		}
		// board객체로 
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		
		board.setMember_id(loginMember.getMember_id());
		
		boardMapper.saveBoard(board);
		return "redirect:/board/list";
	}
	
	// 게시글 목록 출력 
	@GetMapping("list")
	public String list(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) { // name 속성은 꺼내고 싶은 세션 이름
//		HttpSession session = request.getSession(false);
//		// 로그인 하지 않으면 로그인 페이지로 
//		if(session == null || session.getAttribute("loginMember") == null) {
//			return "redirect:/member/login";
//		} 
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		List<Board> findAllBoard = boardMapper.findAllBoard();
		model.addAttribute("boards", findAllBoard);
//		log.info("findAllBoard : {}", findAllBoard);
		
		
		// 로그인 했으면 게시판으로 
		return "board/list";
	}
	
	// 게시글 읽기 
	@GetMapping("read")
	public String read(@SessionAttribute(name = "loginMember") Member loginMember,
						@RequestParam Long board_id, Model model) {
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		Board board = boardMapper.findBoardById(board_id);

		if(board == null) {
			log.info("게시글 없음");
			return "redirect:/board/list";
		}
		board.addHit();
		
		boardMapper.updateBoard(board);
	
		model.addAttribute("board", board);
		
		return "board/read";
	}
	
	// 수정하기 페이지 이동
	@GetMapping("update")
	public String update(@SessionAttribute(name = "loginMember") Member loginMember,
						 @RequestParam Long board_id, Model model) {
		
		if(loginMember == null) {
			return "redirect:/board/list";
		}
		Board findBoardById = boardMapper.findBoardById(board_id);
		model.addAttribute("board", findBoardById);
		return "board/update";	
	}
	
	// 게시글 수정
	@PostMapping("update")
	public String updateBoard(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							  @RequestParam Long board_id, 
							 @Validated @ModelAttribute("board") BoardUpdateForm updateBoard, 
							 Model model, BindingResult result) {
		log.info("board_id : {} , board : {} " , board_id, updateBoard);
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		if(result.hasErrors()) {
			return "board/update";
		}
		
		Board board = boardMapper.findBoardById(board_id);
		
		if(updateBoard.getTitle() == null || updateBoard.getContents() == null) {
			result.reject("updateError", "수정된 제목과 내용을 입력해주세요");
		}
		
		// Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
		if(board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		board.setTitle(updateBoard.getTitle());
		board.setContents(updateBoard.getContents());
		
		boardMapper.updateBoard(board);
		
		return "redirect:/board/list";
		
		
	}
	
	// 게시글 삭제
	// 처음 작성할 때 만들었던 패스워드를 입력하고 삭제하기 버튼을 누르면 삭제되도록
	@GetMapping("delete")
	public String remove(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Long board_id) {
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		Board board = boardMapper.findBoardById(board_id);
		
		// id에 해당하는 게시글이 있고, 입력한 패스워드가 일치하면 게시글을 삭제
		if(board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		boardMapper.removeBoard(board_id);
		return "redirect:/board/list";
	}
}
