package app.labs.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.notice.model.Notice;
import app.labs.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    //공지사항 작성
    @GetMapping("/notice_insert")
    public String submitNotice(Notice notice, HttpSession session) {
		return "thymeleaf/notice/notice_insert";
	}
    
    //공지사항 저장
    @PostMapping("/submit_notice")
    public String submitNotice(
//    							@RequestParam("noticeText") String noticeText, 
    							Notice notice,
    							HttpSession session, RedirectAttributes redirectAttributes) {
    	
    	log.info(notice.getTitle());
    	log.info(notice.getText());
        try {
            // Content 객체 생성하여 CLOB에 저장할 데이터 준비
//        	notice.setText(noticeText);  // HTML 형식의 텍스트와 이미지 포함된 내용
        	notice.setUserId((String)session.getAttribute("userid"));
        	log.info("try");
            // CLOB에 저장
        	noticeService.createNotice(notice);
//        	redirectAttributes.addFlashAttribute("message", 
//					"메시지를 보냈습니다.");
        } catch (Exception e) {
            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/notices";
    }
    
    //공지사항 상세조회
    @GetMapping("/notice/{noticeId}")
    public String getNotice(@PathVariable("noticeId") String noticeId,
    						Model model, Notice notice) {
        notice = noticeService.getNotice(noticeId);
//        log.info(notice.getText());
//        notice.setText(notice.getText().replace("<br>", "\n"));
        model.addAttribute("boardNotice", notice);
        return "thymeleaf/notice/notice_select";
    }
    
    //전체 공지사항
    @GetMapping("/notices")
    public String getAllNotice(
//    					@PathVariable String fileName, 
    					HttpSession session, Model model) {
    	List<Notice> notices = noticeService.getAllNotice();
    	// 파일 경로로 파일 불러오기 추가
        model.addAttribute("notices", notices);
		return "thymeleaf/notice/notices";
    }
}