package app.labs.register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.register.controller.MyPageController;
import app.labs.register.dao.MyPageContentRepository;
import app.labs.register.model.MyContent;
import app.labs.register.model.PostComment;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassMyPageService implements MyPageService {

    @Autowired
    private MyPageContentRepository contentRepository;
    
    @Autowired
    private MyPageContentRepository myPageContentRepository;
    
   

    @Override
    public List<MyContent> getContentsByUserId(String userId) {
        return contentRepository.findContentsByUserId(userId);
    }
    
    

    @Override
    public List<PostComment> getCommentsByUserId(String userId) {
        return myPageContentRepository.findCommentsByUserId(userId);
    }
    
    @Override
    public List<MyContent> getPostsByUserId(String userId) {
        List<MyContent> myPosts = myPageContentRepository.findPostsByUserId(userId);
        log.info("Retrieved posts from repository for user ID {}: {}", userId, myPosts);
        return myPosts;
    }
    
    public MyContent getPostById(int contentId) {
        return myPageContentRepository.findPostById(contentId);
    }

 
    
}
    

