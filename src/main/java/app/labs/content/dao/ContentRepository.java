package app.labs.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import app.labs.content.model.Content;

@Mapper
public interface ContentRepository {
    Content getContentInfo(int contentId);
    void createContent(Content content);
    void editContent(Content content);
    void deleteContent(int contentId);
    List<Content> getAllContents();
}
