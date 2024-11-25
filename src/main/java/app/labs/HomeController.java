package app.labs;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.labs.content.model.Content;
import app.labs.notice.model.Notice;
import app.labs.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	private static final Pattern IMG_PATTERN = Pattern.compile("<img[^>]+src=\"([^\"]+)\"[^>]*>");
	
	@Autowired
	NoticeService noticeService;
	
	public String getFirstImageUrl(String content) {
        Matcher matcher = IMG_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null; // 첫 번째 이미지가 없을 경우 null 반환
    }
	

//    public String stripHtmlTags(String html) {
//        if (html == null || html.isEmpty()) {
//            return "";
//        }
//        // `img` 태그와 그 사이의 내용을 제거
//        String withoutImgTags = html.replaceAll("<img[^>]*>(.*?)</img>", "");
//        // 나머지 HTML 태그 제거
//        return withoutImgTags.replaceAll("<[^>]*>", "");
//	}
    
    public String stripHtmlTags(String html) {
        if (html == null || html.isEmpty()) {
            return "";
        }
        // `img` 태그와 그 사이의 내용을 제거
        String withoutImgTags = html.replaceAll("<img[^>]*>(.*?)</img>", "");
        // 나머지 HTML 태그 제거, 단 `<br>` 태그는 유지
        return withoutImgTags.replaceAll("<(?!br\\b)[^>]*>", "");
    }


	
	@GetMapping("/")
	public String index(Model model) {
//		가장 최신 공지사항 1개 가져오기
		Notice notice = noticeService.getOneNotice();
//		게시판 추천순으로 3개 게시글 가져오기
		List<Content> contents = noticeService.getContents();
		String plainText = stripHtmlTags(notice.getText());
		String imgUrl = getFirstImageUrl(notice.getText());
		if(plainText.length()>=200)
			notice.setText(plainText.substring(0, 200) + "...");
		else
			notice.setText(plainText);
//		String imgUrl = getFirstImageUrl(notice.getText());
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("notice", notice);
		model.addAttribute("contents", contents);
		return "thymeleaf/main_content";
	}
	
	@GetMapping("/content")
	public String homeIndex() {
		 return "thymeleaf/content/home";
	 }
	
	@GetMapping("/register")
	public String registerIndex() {
		 return "thymeleaf/register/home";
	 }
	
	@GetMapping("/profile")
	public String profileIndex() {
		 return "thymeleaf/profile/home";
	 }
	
	@GetMapping("/message")
	public String messageIndex() {
		 return "thymeleaf/message/home";
	 }
}