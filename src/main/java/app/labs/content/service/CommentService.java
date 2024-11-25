package app.labs.content.service;

import java.util.List;

import app.labs.content.model.Comment;

public interface CommentService {
    List<Comment> getCommentsByContentId(int contentId); // 댓글 조회
    Comment getCommentById(int commentId); // 댓글 조회
    void addComment(Comment comment); // 댓글 추가
    void deleteComment(int commentId, String userId); // 댓글 삭제
}
