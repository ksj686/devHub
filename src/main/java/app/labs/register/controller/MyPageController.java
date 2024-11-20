package app.labs.register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import app.labs.register.model.MyPageContent;
import app.labs.register.model.Member;
import app.labs.register.model.PostComment;
import app.labs.register.service.MyPageService;
import app.labs.register.service.MemberService;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String showMyPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userid");
        Member member = memberService.findByUserId(userId);
        model.addAttribute("member", member);

        List<MyPageContent> contents = myPageService.getContentsByUserId(userId);
        model.addAttribute("contents", contents);

        List<PostComment> comments = myPageService.getCommentsByUserId(userId);
        model.addAttribute("comments", comments);

        return "thymeleaf/mypage";
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
