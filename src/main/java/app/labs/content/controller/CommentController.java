package app.labs.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.labs.content.model.Comment;
import app.labs.content.service.CommentService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class CommentController {
    
	@Autowired
    private CommentService commentService;
	
    // 댓글 목록 조회
    @GetMapping("/contents/{contentId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("contentId") int contentId) {
        List<Comment> comments = commentService.getCommentsByContentId(contentId);
        return ResponseEntity.ok(comments); // 댓글 목록 반환
    }
    
    // 댓글 추가
    @PostMapping("/contents/{contentId}/comments")
    public ResponseEntity<Void> addComment(@PathVariable("contentId") int contentId, @RequestBody Comment comment,  HttpSession session) { 
    																															//오류 발생시 @RequestBody -> @ModelAttribute

        String userId = (String) session.getAttribute("userid"); // 세션에서 userId 가져오기
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 로그인되지 않은 경우
        }
        comment.setUserId(userId);        // 댓글 작성자 ID 설정
        comment.setContentId(contentId); // 댓글이 속한 글 ID 설정
        commentService.addComment(comment); // 댓글 저장
        return ResponseEntity.ok().build();
    }
    
    // 댓글 삭제
    @DeleteMapping("/contents/{contentId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("contentId") int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
