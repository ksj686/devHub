package app.labs.register.service;

import java.util.List;

import app.labs.register.model.MyContent;
import app.labs.register.model.PostComment;

public interface MyPageService {
    List<MyContent> getContentsByUserId(String userId);
    List<PostComment> getCommentsByUserId(String userId);
    List<MyContent> getPostsByUserId(String userId);
	MyContent getPostById(int contentId);
}
