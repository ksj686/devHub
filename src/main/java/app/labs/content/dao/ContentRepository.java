package app.labs.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import app.labs.content.model.Content;

@Mapper
public interface ContentRepository{
    Content getContentInfo(@Param("contentId") int contentId);
    void createContent(Content content);
    void editContent(Content content);
    void deleteContent(@Param("contentId") int contentId);  
    List<Content> getAllContents();
    void increaseRecommend(@Param("contentId") int contentId);
    int getRecommendCount(@Param("contentId") int contentId);
    
    // 페이징된 콘텐츠 가져오기
    List<Content> getPagedContents(@Param("offset") int offset, @Param("limit") int limit);
    // 전체 콘텐츠 개수 가져오기
    @Select("SELECT COUNT(*) FROM CONTENT")
    int getTotalContentCount();
    
    // 추천 이력 확인
    int getRecommendCountByUserAndContent(@Param("contentId") int contentId, @Param("userId") String userId);
    // 추천 기록 추가
    void addRecommendHistory(@Param("contentId") int contentId, @Param("userId") String userId);
    
    List<Content> findByTitleContaining(@Param("title") String title);
    List<Content> findByUserIdContaining(@Param("userId") String userId);
}
