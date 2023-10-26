package com.example.board.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.repository.BoardMapper;
import com.example.board.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Transactional // saveBoard, saveFile 둘중 하나라도 오류가 나면 오류 ,둘다 처리가 되야함
	public void saveBoard(Board board, MultipartFile file) {
		
		// 데이터베이스에 저장한다
		boardMapper.saveBoard(board);

		// 첨부파일 저장
		if(file != null && file.getSize() > 0 ) {
			// 첨부파일을 서버에 저장
			AttachedFile saveFile = fileService.saveFile(file);
			// 첨부파일 내용을 데이터베이스에 저장
			saveFile.setBoard_id(board.getBoard_id());
			boardMapper.saveFile(saveFile);
		} else return;
	}
	
	public List<Board> findAllBoard(String searchText, int startRecored, int countPerpage){
		RowBounds rowBounds = new RowBounds(startRecored, countPerpage);
		return boardMapper.findBoards(searchText, rowBounds);
		
	}
	
	public Board findBoardbyId(Long board_id) {
		return boardMapper.findBoardById(board_id);
		
	}
	@Transactional
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = boardMapper.findBoardById(updateBoard.getBoard_id());
		
		if(board != null) {
			boardMapper.updateBoard(updateBoard);
			// 첨부파일 정보를 가져온다.
			AttachedFile attachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
			
			if(attachedFile != null && ( (isFileRemoved) || (file != null && file.getSize() > 0))) {
				// 파일 삭제를 요청했거나 새로운 파일이 업로드 됐으면 기존 파일을 삭제한다.
				removeAttachedFile(attachedFile.getAttachedFile_id());
			}
		}
		// 새로 저장할 파일이 있을 경우 저장한다.
		if(file != null && file.getSize() > 0 ) {
			// 첨부파일을 서버에 저장한다.
			AttachedFile savedFile = fileService.saveFile(file);
			// 데이터베이스 저장될 board_id를 세팅 
			savedFile.setBoard_id(updateBoard.getBoard_id());
			// 첨부파일 내용을 데이터베이스에 저장
			boardMapper.saveFile(savedFile);
			
		}
	}
	
	@Transactional
	public void removeAttachedFile(Long attachedFile_id) {
		AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
		if(attachedFile != null) {
			// 서버에서 삭제
			String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
			fileService.deleteFile(fullPath);
			// 데이터베이스에서 삭제
			boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
		}
	}
	
	public void removeBoard(Long board_id) {
		boardMapper.removeBoard(board_id);
		
	}
	
	public Board readBoard(Long board_id) {
		Board board = findBoardbyId(board_id);
		board.addHit();
		updateBoard(board , false, null);
		return board;
	}

	public AttachedFile findFileByBoardId(Long id) {
		return boardMapper.findFileByBoardId(id);
	}

	public int getTotal(String searchText) {
		return boardMapper.getTotal(searchText);
	}

	

}
