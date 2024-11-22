package app.labs.content.service;

import java.util.List;

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

	@Override
	public void addComment(Comment comment) {
		commentRepository.addComment(comment);
	}

	@Override
	public void deleteComment(int commentId) {
		commentRepository.deleteComment(commentId);
	}

}
