package app.labs.register.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import app.labs.register.model.MyContent;
import app.labs.register.model.PostComment;

@Mapper
public interface MyPageContentRepository {
    List<MyContent> findContentsByUserId(@Param("userId") String userId);
    List<PostComment> findCommentsByUserId(@Param("userId") String userId);
    List<MyContent> findPostsByUserId(@Param("userId") String userId); // 기존 MyPostsByUserId 대신
    List<MyContent> findFilteredPostsByUserId(@Param("userId") String userId); // 추가된 메서드

   
        MyContent findPostById(@Param("contentId") int contentId);
    


}


   


