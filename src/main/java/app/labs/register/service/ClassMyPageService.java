package app.labs.register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.register.dao.MyPageContentRepository;
import app.labs.register.model.MyPageContent;
import app.labs.register.model.PostComment;

@Service
public class ClassMyPageService implements MyPageService {

    @Autowired
    private MyPageContentRepository contentRepository;

    @Override
    public List<MyPageContent> getContentsByUserId(String userId) {
        return contentRepository.findContentsByUserId(userId);
    }

    @Override
    public List<PostComment> getCommentsByUserId(String userId) {
        return contentRepository.findCommentsByUserId(userId);
    }
}
