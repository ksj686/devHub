package app.labs.register.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.register.model.MyPageContent;
import app.labs.register.model.PostComment;

@Mapper
public interface MyPageContentRepository {
    // 사용자가 작성한 게시글 목록을 가져오는 메서드
    List<MyPageContent> findContentsByUserId(@Param("userId") String userId);

    // 사용자가 작성한 댓글 목록을 가져오는 메서드
    List<PostComment> findCommentsByUserId(@Param("userId") String userId);
}
