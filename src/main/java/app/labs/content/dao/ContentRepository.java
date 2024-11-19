package app.labs.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.content.model.Content;

@Mapper
public interface ContentRepository {
    Content getContentInfo(int contentId);
    void createContent(Content content);
    void editContent(Content content);
    void deleteContent(@Param("contentId") int contentId, @Param("userId") String userId);  
    List<Content> getAllContents();
    void increaseRecommend(@Param("contentId") int contentId);
    int getRecommendCount(@Param("contentId") int contentId);
}
