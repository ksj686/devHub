package app.labs.content.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.content.dao.CommentRepository;
import app.labs.content.model.Comment;

@Service
public class BasicCommentService implements CommentService {
	
    @Autowired
    private CommentRepository commentRepository;

	@Override
	public List<Comment> getCommentsByContentId(int contentId) {	
		return commentRepository.getCommentsByContentId(contentId);
	}
	
	public Comment getCommentById(@Param("commentId") int commentId) {
		return commentRepository.getCommentById(commentId);
	}

	@Override
	public void addComment(Comment comment) {
		commentRepository.addComment(comment);
	}
	

    @Override
    public void deleteComment(int commentId, String userId) {
        // 댓글 존재 여부 확인
        Comment comment = commentRepository.getCommentById(commentId);
        if (comment == null) {
        	throw new IllegalArgumentException("Comment not found");
        }

        // 댓글 작성자와 현재 사용자 비교
        if (!comment.getUserId().equals(userId)) {
            throw new IllegalStateException("You are not authorized to delete this comment");
        }

        // 댓글 삭제
        commentRepository.deleteComment(commentId);
    }

}
