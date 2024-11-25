package app.labs.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.content.model.Comment;

@Mapper
public interface CommentRepository {
    List<Comment> getCommentsByContentId(@Param("contentId") int contentId); // 특정 글의 댓글 조회
    Comment getCommentById(@Param("commentId") int commentId); // 특정 글의 댓글 조회 2
    void addComment(Comment comment); // 댓글 추가
    void deleteComment(@Param("commentId") int commentId); // 댓글 삭제
}
