package app.labs.register.service;

import java.util.List;

import app.labs.register.model.MyPageContent;
import app.labs.register.model.PostComment;

public interface MyPageService {
    List<MyPageContent> getContentsByUserId(String userId);
    List<PostComment> getCommentsByUserId(String userId);
}
