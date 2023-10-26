package com.example.board.controller;


import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;
import com.example.board.service.BoardService;
import com.example.board.util.FileService;
import com.example.board.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("board")
@RequiredArgsConstructor  // 생성자 주입 방식으로 주입시켜줌
@Controller
public class BoardController {
	
	// 데이터베이스 접근을 위한 BoardMapper 필드 선언
	private final BoardService boardService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
	
	// 페이징 처리를 위한 상수값
	private final int countPerPage = 10; // 한 페이지에 몇개를 보여줄건지
	private final int pagePerGroup = 5;  // 한 페이지에 몇개 페이지를 써줄건지
	
	
	
	// 게시글 작성 페이지로 이동 
	@GetMapping("write")
	public String writeForm(Model model) {
		model.addAttribute("writeForm", new BoardWriteForm());
		return "board/write";
	}
	
	// 게시글 저장
	@PostMapping("write")
	public String write(@SessionAttribute(name = "loginMember" ,required = false) Member loginMember,
						@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm, 
						 BindingResult result , @RequestParam(required = false) MultipartFile file) {
		log.info("boardWriteForm : {}", boardWriteForm);
		log.info("file : {}" , file);
		
		if(result.hasErrors()) {
			return "board/write";
		}
		// 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		
		// board 객체에 로그인한 사용자의 아이디를 추가한다.
		board.setMember_id(loginMember.getMember_id());
		
		boardService.saveBoard(board, file);
		
		
		return "redirect:/board/list";
	}
	
	// 게시글 목록 출력 
	@GetMapping("list")
	public String list(@RequestParam(value="page" ,defaultValue = "1") int page, Model model,
					   @RequestParam(value="searchText", defaultValue = "") String searchText) { // name 속성은 꺼내고 싶은 세션 이름

		log.info("검색어 : {}", searchText);
		int total = boardService.getTotal(searchText);
		
		PageNavigator nav = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		// 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
		List<Board> findAllBoard = boardService.findAllBoard(searchText,nav.getStartRecord(), nav.getCountPerPage());
		
		// Board 리스트를 model에 저장한다.
		model.addAttribute("boards", findAllBoard);
		
		model.addAttribute("nav", nav);
		
		model.addAttribute("searchText", searchText);
		
		// 로그인 했으면 게시판으로 
		return "board/list";
	}
	
	// 게시글 읽기 
	@GetMapping("read")
	public String read(@RequestParam Long board_id, Model model) {
		
		Board board = boardService.readBoard(board_id);

		if(board == null) {
			log.info("게시글 없음");
			return "redirect:/board/list";
		}
		
		AttachedFile attachedFile = boardService.findFileByBoardId(board_id);
		
//		log.info("attachedFile : {}", attachedFile);
	
		model.addAttribute("file", attachedFile);
		
		model.addAttribute("board", board);
		
		
		return "board/read";
	}
	
	// 수정하기 페이지 이동
	@GetMapping("update")
	public String update(@SessionAttribute(name = "loginMember") Member loginMember,
						 @RequestParam Long board_id, Model model) {
		
		
		Board findBoardById = boardService.findBoardbyId(board_id);
		// Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
		if(findBoardById == null || !findBoardById.getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		
		AttachedFile attachedFile = boardService.findFileByBoardId(board_id);
		
		model.addAttribute("file", attachedFile);
		
		model.addAttribute("board", Board.toBoardUpdateForm(findBoardById));
		return "board/update";
	}
	
	// 게시글 수정
	@PostMapping("update")
	public String updateBoard(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
							  @RequestParam Long board_id, 
							  @Validated @ModelAttribute("board") BoardUpdateForm updateBoard, 
							  Model model, BindingResult result , @RequestParam(required = false) MultipartFile file) {
		log.info("board_id : {} , board : {} " , board_id, updateBoard);
		
		
		
		if(result.hasErrors()) {
			return "/board/update"; // board_id는 그대로 갖고 있어야 하기 때문에 redirect 하지 않음
		}
		
		Board board = boardService.findBoardbyId(board_id);
		
		if(board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		board.setTitle(updateBoard.getTitle());
		board.setContents(updateBoard.getContents());
		
		boardService.updateBoard(board ,updateBoard.isFileRemoved(), file);
		return "redirect:/board/list";
	}
	
	// 게시글 삭제
	// 처음 작성할 때 만들었던 패스워드를 입력하고 삭제하기 버튼을 누르면 삭제되도록
	@GetMapping("delete")
	public String remove(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
						 @RequestParam Long board_id) {
		
		log.info("id : {}", board_id);
		
		Board board = boardService.findBoardbyId(board_id);
		
		if(board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		
		// 파일 존재 여부 체크 
		AttachedFile file = boardService.findFileByBoardId(board_id);
		
		// 첨부파일 서버와 DB에서 삭제
		if(file != null) {
			boardService.removeAttachedFile(file.getAttachedFile_id());
		}
		
		boardService.removeBoard(board_id);

		return "redirect:/board/list";
	}
	
	@GetMapping("download/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException{
		// 첨부파일 아이디로 첨부파일 정보를 가져온다.
		AttachedFile attachedFile = boardService.findFileByBoardId(id);
		// 다운로드 하려는 파일의 정대경로 값을 만든다.
		String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
		
		UrlResource resource = new UrlResource("file:" + fullPath);
		
		// 한글 파일명이 깨지지 않도록 UTF_8로 파일명을 인코딩한다.
		String encodingFileName = UriUtils.encode(attachedFile.getOriginal_filename(), 
												  StandardCharsets.UTF_8);
		// 응답헤더에 담을 Content Disposition 값을 생성한다.
		String contentDisposition = "attachment; filename=\"" + encodingFileName + "\"";
		
		return ResponseEntity.ok()
							 .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
							 .body(resource);
	}
	
	
}
