package app.labs.register.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.register.model.Member;
import app.labs.register.service.BasicMemberService;
import app.labs.register.service.MemberService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    private static final Logger logger = LoggerFactory.getLogger(BasicMemberService.class);

    // 아이디 중복 확인을 위한 API
    @GetMapping("/members/check-userid")
    public ResponseEntity<Boolean> checkUserId(@RequestParam(name = "userId") String userId) {
        boolean isDuplicated = memberService.isUserIdDuplicated(userId);
        logger.debug("UserID check for: {}, isDuplicated: {}", userId, isDuplicated);

        return ResponseEntity.ok(isDuplicated);
    }

    // 회원가입 폼을 반환하는 메서드
    @GetMapping("/register/insertform")
    public String showInsertForm(Model model) {
        return "insertform";  // templates/insertform.html 파일을 반환
    }
    
    // 기본 메서드들: 서버의 시간을 반환하는 홈 페이지
    @GetMapping(value = {"/members", "/members/"})
    public String home(Model model) {
        model.addAttribute("serverTime", "서버시간");
        return "thymeleaf/register/home";
    }

    // 회원 수를 반환하는 메서드 (상태별)
    @GetMapping("/members/count")
    public String memberCount(@RequestParam(value = "status", required = false, defaultValue = "all") String status, Model model) {
        if ("all".equals(status)) {
            model.addAttribute("count", memberService.getMemberCount());
        } else {
            // 상태별 회원 수를 반환하는 로직이 필요하다면 여기에 추가
        }
        return "thymeleaf/register/count";
    }

    // 회원가입 페이지를 반환하는 메서드
    @GetMapping("/members/insert")
    public String insertMember(Model model) {
        return "thymeleaf/register/insertform";
    }

    // 회원가입 처리 메서드
    @PostMapping("/members/insert")
    public String insertMember(Member member, RedirectAttributes redirectAttributes) {
        try {
            memberService.insertMember(member);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료 되었습니다!");
            return "redirect:/members/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "회원가입에 실패했습니다. 다시 시도해주세요."); // 실패 메시지
            return "redirect:/members/insert";
        }
    }

    // 마이페이지 (회원 정보 확인) 메서드
    @GetMapping("/members/mypage")
    public String showMemberProfile(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userid");
        if (userId != null) {
            Member member = memberService.findByUserId(userId);
            model.addAttribute("member", member);
            return "thymeleaf/register/mypage";
        } else {
            return "redirect:/members/login";
        }
    }

    // 회원 정보 수정 폼을 반환하는 메서드
    @GetMapping("/members/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userid");
        logger.debug("GET /members/edit - Session userId: {}", userId);

        if (userId != null) {
            Member member = memberService.findByUserId(userId);
            logger.debug("GET /members/edit - Member: {}", member);
            model.addAttribute("member", member);
            return "thymeleaf/register/edit_profile";
        } else {
            return "redirect:/members/login";
        }
    }

    // 회원 정보 수정 처리 메서드
    @PostMapping("/members/edit")
    public String updateProfile(@ModelAttribute Member member, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userid");
        logger.debug("POST /members/edit - Session userId: {}", userId);
        logger.debug("Updating member: {}", member);

        if (userId != null && userId.equals(member.getUserId())) {
            memberService.updateMember(member);
            redirectAttributes.addFlashAttribute("message", "수정 되었습니다!");
            return "redirect:/members/mypage";
        } else {
            return "redirect:/members/login";
        }
    }

    // 로그인 폼을 반환하는 메서드
    @GetMapping("/members/login")
    public String loginMember(Model model) {
        return "thymeleaf/register/loginform";
    }

    // 로그인 처리 메서드
    @PostMapping("/members/login")
    public String loginMember(@RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd, HttpSession session, RedirectAttributes redirectAttrs) {
        Member member = memberService.findByUserId(userId);
        if (member != null) {
            if (member.getUserPwd().equals(userPwd)) {
                session.setMaxInactiveInterval(600); // 10분
                session.setAttribute("userid", userId);
                return "redirect:/";
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                session.invalidate();
                redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
                return "redirect:/members/login";
            }
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
            session.invalidate();
            redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
            return "redirect:/members/login";
        }
    }

    // 로그아웃 메서드
    @GetMapping("/members/logout")
    public String logoutMember(HttpSession session) {
        session.invalidate();
        return "redirect:/members/list";
    }

    // 아이디 찾기 폼을 반환하는 메서드
    @GetMapping("/members/find-username")
    public String showFindUsernameForm() {
        return "thymeleaf/register/find_username";
    }

    // 아이디 찾기 처리 메서드
    @PostMapping("/members/find-username")
    public String findUsername(@RequestParam(value = "emailOrPhone") String emailOrPhone, Model model) {
        String userId = memberService.findUserIdByEmailOrPhone(emailOrPhone);
        if (userId != null) {
            model.addAttribute("message", "귀하의 아이디는 " + userId + "입니다.");
            model.addAttribute("messageType", "success"); // 성공 메시지
        } else {
            model.addAttribute("message", "등록된 이메일 또는 전화번호를 찾을 수 없습니다.");
            model.addAttribute("messageType", "error"); // 오류 메시지
        }
        return "thymeleaf/register/find_username";
    }

    // 비밀번호 찾기 폼을 반환하는 메서드
    @GetMapping("/members/find-password")
    public String showFindPasswordForm() {
        return "thymeleaf/register/find_password";
    }

    // 비밀번호 찾기 처리 메서드
    @PostMapping("/members/find-password")
    public String findPassword(@RequestParam(value = "emailOrPhone") String emailOrPhone, Model model) {
        String password = memberService.findPasswordByEmailOrPhone(emailOrPhone);
        if (password != null) {
            model.addAttribute("message", "귀하의 비밀번호는 " + password + "입니다.");
            model.addAttribute("messageType", "success"); // 성공 메시지

        } else {
            model.addAttribute("message", "등록된 이메일 또는 전화번호를 찾을 수 없습니다.");
            model.addAttribute("messageType", "error"); // 오류 메시지

        }
        return "thymeleaf/register/find_password";
    }
}
