// ContentController.java
package app.labs.content.controller;

import app.labs.content.model.Content;
import app.labs.content.service.ContentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public String getAllContents(Model model) {
        List<Content> contents = contentService.getAllContents();
        model.addAttribute("contents", contents);
        return "thymeleaf/content/contents";
    }
   
    @GetMapping("/{contentId}")
    public String getContentDetails(@PathVariable("contentId") int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId); // ID를 통해 글 조회
        model.addAttribute("content", content); // 모델에 글 데이터를 추가
        return "thymeleaf/content/contents_details"; // 상세 페이지로 이동
    }

    @GetMapping("/new")
    public String createContentForm(Model model) {
        model.addAttribute("content", new Content());
        return "thymeleaf/content/contents_form";
    }

    @PostMapping
    public String createContent(Content content) {
        contentService.createContent(content);
        return "redirect:/contents";
    }

    @GetMapping("/edit/{contentId}")
    public String editContentForm(@PathVariable int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId);
        model.addAttribute("content", content);
        return "thymeleaf/content/contents_form";
    }

    @PostMapping("/edit/{contentId}")
    public String editContent(@PathVariable int contentId, Content content) {
        content.setContentId(contentId);
        contentService.editContent(content);
        return "redirect:/contents";
    }

    
    @DeleteMapping("/{contentId}")
    @ResponseBody
    public ResponseEntity<String> deleteContent(
            @PathVariable int contentId,
            @RequestParam String userId) {// userId를 요청 파라미터로 받음
    	log.info("deleteContent 실행");
    	
        try {
            contentService.deleteContent(contentId, userId);
            log.info("삭제 성공");
            return ResponseEntity.ok("Content deleted successfully.");
        } catch (Exception e) {
        	log.warn("삭제 실패");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("You are not authorized to delete this content.");
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
