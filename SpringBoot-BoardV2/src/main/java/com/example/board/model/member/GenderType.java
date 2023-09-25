package com.example.board.model.member;
/**
 * 서로 연관된 상수들의 집합 
 * 미리 GenderType으로 해놓으면 대소문자 mALE 등 오류 잡아줌
 */
public enum GenderType {
	MALE("남성"),
	FEMALE("여성"),
	TRANS("중성");
	
	private String description;
	
	private GenderType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
