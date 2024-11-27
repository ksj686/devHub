package app.labs.register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.labs.register.model.Member;
import app.labs.register.model.MyContent;
import app.labs.register.model.PostComment;
import app.labs.register.service.BasicMemberService;
import app.labs.register.service.MemberService;
import app.labs.register.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MemberService memberService;

    //게시글로 연결 
    @GetMapping("/members/mypage")
    public String showMyPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userid");
        if (userId == null) {
            return "redirect:/members/login";
        }

        log.info("User ID from session: {}", userId);

        Member member = memberService.findByUserId(userId);
        log.info("Retrieved member: {}", member);

        List<MyContent> myPosts = myPageService.getPostsByUserId(userId);
        List<PostComment> myComments = myPageService.getCommentsByUserId(userId);
        log.info("Retrieved posts: {}", myPosts);

        model.addAttribute("member", member);
        model.addAttribute("myPosts", myPosts);
        model.addAttribute("myComments", myComments);
        return "thymeleaf/register/mypage";
    }



    @PostMapping("/update")
    public String updateMember(@ModelAttribute Member member, HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        if (userId != null && userId.equals(member.getUserId())) {
            memberService.updateMember(member);
        }
        return "redirect:/mypage";
    }
}
