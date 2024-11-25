package app.labs.content.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.labs.content.model.Content;
public interface ContentService {
	
	Content getContentInfo(int contentId); // 글 내용 조회
	void createContent(Content content); // 글 작성
	void editContent(Content content); // 글 수정
	boolean deleteContent(int contentId, String enteredUserId); // 글 삭제
	List<Content>getAllContents(); //모든 글 조회
	void increaseRecommend(int contentId); //추천수 증가
	int getRecommendCount(int contentId); //추천수 조회
	
    List<Content> getPagedContents(@Param("offset") int page, @Param("offset") int size);
    int getTotalContentCount();
    
    
}	
