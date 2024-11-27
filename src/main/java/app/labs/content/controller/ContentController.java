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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String editContentForm(@PathVariable("contentId") int contentId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        String loggedInUserId = (String) session.getAttribute("userid"); // 현재 로그인한 사용자
        Content content = contentService.getContentInfo(contentId); // 글 정보 조회

        // 로그인된 사용자와 글 작성자 비교
        if (!content.getUserId().equals(loggedInUserId)) {
            // 권한이 없으면 에러 메시지를 추가하고 상세 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("error", "작성자만 글을 수정할 수 있습니다.");
            return "redirect:/contents/" + contentId; // 글 상세 페이지로 이동
        }

        // 권한이 있으면 기존 내용을 모델에 추가하고 수정 페이지로 이동
        model.addAttribute("content", content);
        return "thymeleaf/content/contents_edit"; // 수정 페이지로 이동
    }
    
    @PostMapping("/contents/edit/{contentId}")
    public String editContent(@PathVariable("contentId") int contentId,
                              @ModelAttribute Content content,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        String loggedInUserId = (String) session.getAttribute("userid");

        // 기존 글 정보 확인
        Content existingContent = contentService.getContentInfo(contentId);

        // 권한 확인: 작성자가 아니면 수정 불가
        if (!existingContent.getUserId().equals(loggedInUserId)) {
            redirectAttributes.addFlashAttribute("error", "작성자만 글을 수정할 수 있습니다.");
            return "redirect:/contents/" + contentId;
        }

        // 데이터 저장
        content.setContentId(contentId);
        content.setUserId(loggedInUserId); // 작성자 유지
        contentService.editContent(content);

        redirectAttributes.addFlashAttribute("message", "글이 성공적으로 수정되었습니다.");
        return "redirect:/contents/" + contentId; // 수정 완료 후 상세 페이지로 이동
    }
    
    @ResponseBody
    @DeleteMapping("/contents/{contentId}")
    public ResponseEntity<Void> deleteContent(
            @PathVariable("contentId") int contentId,
            HttpSession session) {

        // 로그인한 사용자 ID 가져오기
        String loggedInUserId = (String) session.getAttribute("userid");
        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 로그인 안 된 경우
        }

        // 글 정보 가져오기
        Content content = contentService.getContentInfo(contentId);
        if (content == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 글이 없는 경우
        }

        // 작성자와 로그인한 사용자 비교
        if (!content.getUserId().equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한 없는 경우
        }

        // 글 삭제
        contentService.deleteContent(contentId, loggedInUserId);
        return ResponseEntity.ok().build();
    }
    
    
    // 추천 수 증가
    @PostMapping("/contents/{contentId}/recommend")
    public ResponseEntity<String> recommendContent(@PathVariable("contentId") int contentId, HttpSession session) {
        // 로그인 유저 ID 확인
        String userId = (String) session.getAttribute("userid");
        if (userId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        
        // 추천 처리
        boolean isRecommended = contentService.recommendContent(contentId, userId);
        if (!isRecommended) {
            return ResponseEntity.status(403).body("이미 추천한 글입니다.");
        }

        return ResponseEntity.ok("추천 성공!");
    }

    // 추천 수 조회
    @GetMapping("/contents/{contentId}/recommend")
    public ResponseEntity<Integer> getRecommendCount(@PathVariable("contentId") int contentId) {
        int recommendCount = contentService.getRecommendCount(contentId);
        return ResponseEntity.ok(recommendCount);
    }
    
    @GetMapping("/contents/search")
    public List<Content> searchContents(
            @RequestParam("filter") String filter,
            @RequestParam("query") String query) {
        if (filter.equals("title")) {
            return contentService.searchByTitle(query);
        } else if (filter.equals("userId")) {
            return contentService.searchByUserId(query);
        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    }
}
