// ContentController.java
package app.labs.content.controller;

import app.labs.content.model.Comment;
import app.labs.content.model.Content;
import app.labs.content.service.CommentService;
import app.labs.content.service.ContentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping
public class ContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private CommentService commentService;

//    @GetMapping("/contents")
//    public String getAllContents(Model model) {
//        List<Content> contents = contentService.getAllContents();
//        model.addAttribute("contents", contents);
//        return "thymeleaf/content/contents";
//    }
    
    @GetMapping("/contents")
    public String getContents(
            @RequestParam(name = "page", defaultValue = "1") int page,  // 기본 페이지는 1
            @RequestParam(name = "size", defaultValue = "10") int size, // 한 페이지 크기는 20
            Model model) {

        // 페이징 처리된 데이터 가져오기
        List<Content> contents = contentService.getPagedContents(page, size);
        int totalContents = contentService.getTotalContentCount();
        int totalPages = (int) Math.ceil((double) totalContents / size);

        // 데이터 모델에 추가
        model.addAttribute("contents", contents);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "thymeleaf/content/contents"; // View 이름
    }

  
    @GetMapping("/contents/{contentId}")
    public String getContentDetails(@PathVariable("contentId") int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId); // ID를 통해 글 조회
        List<Comment> comments = commentService.getCommentsByContentId(contentId); //ID를 통해 댓글 조회
        model.addAttribute("content", content); // 모델에 글 데이터를 추가
        model.addAttribute("comments", comments); // 모델에 댓글 데이터 추가
        return "thymeleaf/content/contents_details"; // 상세 페이지로 이동
    }

    @GetMapping("/contents/new")
    public String createContentForm(Model model) {
        model.addAttribute("content", new Content());
        return "thymeleaf/content/contents_form";
    }

//    @PostMapping("/contents")
//    public String createContent(@ModelAttribute Content content) {
//        contentService.createContent(content);
//        return "redirect:/contents";
//    }
    
    @PostMapping("/contents")
    public String createContent(@ModelAttribute Content content, HttpSession session) {
        String userId = (String) session.getAttribute("userid"); // 세션에서 userId 가져오기
        if (userId == null) {
            return "redirect:/members/login"; // 로그인되지 않은 상태라면 로그인 페이지로 리다이렉트
        }

        content.setUserId(userId);          // 글 작성자 ID 설정
        contentService.createContent(content); // 글 저장
        return "redirect:/contents";       // 글 목록 페이지로 리다이렉트
    }

    @GetMapping("/contents/edit/{contentId}")
    public String editContentForm(@PathVariable("contentId") int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId);
        model.addAttribute("content", content);
        return "thymeleaf/content/contents_edit";
    }

    @PostMapping("/contents/edit/{contentId}")
    public String editContent(@PathVariable("contentId") int contentId, @ModelAttribute Content content) {
        content.setContentId(contentId);
        contentService.editContent(content);
        return "redirect:/contents/" + contentId;
    }

    @ResponseBody
    @DeleteMapping("/contents/{contentId}")
    public ResponseEntity<Void> deleteContent( @PathVariable("contentId") int contentId, @RequestBody Map<String, String> requestBody) {
            String enteredUserId = requestBody.get("enteredUserId");   // 입력받은 userId
            boolean isDeleted = contentService.deleteContent(contentId, enteredUserId);    // 삭제 로직 실행

            if (isDeleted) {
                return ResponseEntity.ok().build(); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
            }
    }
    
    
    
//    @PostMapping("/{contentId}/recommend")
//    public ResponseEntity<Integer> recommendContent(@RequestParam(name="contentId")  int contentId) {
//    	
//        contentService.increaseRecommend(contentId);         // 추천 수 증가
//        int updatedCount = contentService.getRecommendCount(contentId);   // DB에서 업데이트된 추천 수 조회
//        return ResponseEntity.ok(updatedCount);        // 업데이트된 추천 수 반환
//    }
    
}
