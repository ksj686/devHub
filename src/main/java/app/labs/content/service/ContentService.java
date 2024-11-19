package app.labs.content.service;

import java.util.List;

import app.labs.content.model.Content;
public interface ContentService {
	
	Content getContentInfo(int contentId); // 글 내용 조회
	void createContent(Content content); // 글 작성
	void editContent(Content content); // 글 수정
	void deleteContent(int contentId, String userId); // 글 삭제
	List<Content>getAllContents(); //모든 글 조회
//	void increaseRecommend(int contentId); //추천수 증가
//	int getRecommendCount(int contentId); //추천수 조회
}	
