package app.labs.register.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.register.model.Member;
import app.labs.register.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);	
	
    @Autowired
    MemberService memberService;
    

    @GetMapping(value = {"/members", "/members/"})
    public String home(Model model) {
        model.addAttribute("serverTime", "서버시간");
        return "thymeleaf/register/home";
    }

    // 카운트 메서드
    @GetMapping("/members/count")  
    public String memberCount(@RequestParam(value = "status", required = false, defaultValue = "all") String status, Model model) {
        if ("all".equals(status)) {
            model.addAttribute("count", memberService.getMemberCount());
        } else {
            // 상태별 회원 수를 반환하는 로직이 필요하다면 여기에 추가
        }
        return "thymeleaf/register/count";
    }

    //회원가입 메서드 
    @GetMapping("/members/insert")
    public String insertMember(Model model) {
        // 필요한 추가 데이터가 있다면 여기서 모델에 추가
        return "thymeleaf/register/insertform";
    }

    @PostMapping("/members/insert")
    public String insertMember(Member member, RedirectAttributes redirectAttributes) {
        try {
            memberService.insertMember(member);
            redirectAttributes.addFlashAttribute("message", "회원가입 되었습니다!");
            return "redirect:/members/login"; // 회원가입 후 로그인 페이지로 리다이렉트
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/members/insert"; // 실패 시 다시 회원가입 페이지로 리다이렉트
        }
    }
    
   //프로필 메서드 
    @GetMapping("/members/mypage")
    public String showMemberProfile(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userid");
        if (userId != null) {
            Member member = memberService.findByUserId(userId);
            model.addAttribute("member", member);
            return "thymeleaf/register/mypage"; // Thymeleaf 템플릿 경로
        } else {
            return "redirect:/members/login";
        }
    }
    
	
	  //프로필 수정 메서드  
	  @GetMapping("/members/edit") public String showEditProfileForm(HttpSession
	  session, Model model) { String userId = (String)
	  session.getAttribute("userid");
	  logger.debug("GET /members/edit - Session userId: {}", userId);
	  
	  if (userId != null) { Member member = memberService.findByUserId(userId);
	  logger.debug("GET /members/edit - Member: {}", member);
	  model.addAttribute("member", member); return
	  "thymeleaf/register/edit_profile"; } else { return "redirect:/members/login";
	     } 
	  }
	 
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

    //로그인 메서드
    @GetMapping("/members/login")
    public String loginMember(Model model) {
        return "thymeleaf/register/loginform";
    }

    @PostMapping("/members/login")
    public String loginMember(@RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd, HttpSession session, RedirectAttributes redirectAttrs) {
        Member member = memberService.findByUserId(userId);
        if (member != null) {
            if (member.getUserPwd().equals(userPwd)) {
                session.setMaxInactiveInterval(600); // 10분
                session.setAttribute("userid", userId);
                return "redirect:/members/mypage";
            } else {
                System.out.println("비밀번호가 일치하지 않습니다."); // 로그 추가
                session.invalidate();
                redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
                return "redirect:/members/login";
            }
        } else {
            System.out.println("사용자를 찾을 수 없습니다."); // 로그 추가
            session.invalidate();
            redirectAttrs.addFlashAttribute("message", "아이디 또는 패스워드가 잘못되었습니다.");
            return "redirect:/members/login";
        }
    }

   @GetMapping("/members/logout")
   public String logoutMember(HttpSession session) {
        session.invalidate();
        return "redirect:/members/list";
    }
}

    


//@GetMapping("/members/list")
//public String getAllMembers(Model model) {
//  List<Member> memberList = memberService.getMemberList();
//  model.addAttribute("memberList", memberList);
//  return "thymeleaf/register/list";
//}

//@GetMapping("/members/{idx}")
//public String getMemberInfo(@PathVariable("idx") int idx, Model model) {
//  Member member = memberService.getMemberInfo(idx);
//  model.addAttribute("member", member);
//  return "thymeleaf/register/view";
//}


/*
 * @GetMapping("/members/update") public String
 * updateMember(@RequestParam("idx") int idx, Model model) {
 * model.addAttribute("member", memberService.getMemberInfo(idx)); return
 * "thymeleaf/register/updateform"; }
 * 
 * @PostMapping("/members/update") public String updateMember(Member member,
 * RedirectAttributes redirectAttributes) { try {
 * memberService.updateMember(member);
 * redirectAttributes.addFlashAttribute("message", "수정 되었습니다!"); // 메시지 설정
 * return "redirect:/members/profile"; // 프로필 페이지로 리디렉션 } catch
 * (RuntimeException e) { redirectAttributes.addFlashAttribute("message",
 * e.getMessage()); return "redirect:/members/update?idx=" + member.getIdx(); //
 * 오류 발생 시 수정 페이지로 리디렉션 } } }
 */

//@GetMapping("/members/delete")
//public String deleteMember(@RequestParam("idx") int idx, Model model) {
//    model.addAttribute("member", memberService.getMemberInfo(idx));
//    return "thymeleaf/register/deleteform";
//}

//@PostMapping("/members/delete")
//public String deleteMember(@RequestParam("idx") Long idx, Model model, RedirectAttributes redirectAttrs) {
//    try {
//        memberService.deleteMember(idx);
//        redirectAttrs.addFlashAttribute("message", idx + "번 회원정보가 삭제되었습니다.");
//        return "redirect:/members/list";
//    } catch (RuntimeException e) {
//        redirectAttrs.addFlashAttribute("message", e.getMessage());
//        return "redirect:/members/list";
//    }
//}

//@GetMapping("/members/json")
//public @ResponseBody List<Member> getMemberJSONList() {
//    return memberService.getMemberList();
//}
//
//@GetMapping("/members/json/{idx}")
//public @ResponseBody Member getMemberJSONInfo(@PathVariable int idx) {
//    return memberService.getMemberInfo(idx);
//}
