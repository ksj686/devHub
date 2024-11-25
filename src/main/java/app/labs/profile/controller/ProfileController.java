package app.labs.profile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.profile.model.Profile;
import app.labs.profile.service.ProfileService;
import app.labs.register.model.Member;
import app.labs.register.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProfileController {

    @Autowired
    ProfileService profileService;
    @Autowired
    MemberService memberService;
    
    @GetMapping("/profile/home")
    public String home(String userId, Model model) {
    	model.addAttribute("serverTime", "서버시간");
    	return "thymeleaf/profile/home";
    }  
    
    @GetMapping("/profile/list")
    public String getAllProfiles(Model model) {
    	List<Profile> profileList = profileService.getProfileList();
    	model.addAttribute("profileList", profileList);
    	return "thymeleaf/profile/list";
    }  
    
    @GetMapping("/profile/{userId}")
    public String getProfileInfo(@PathVariable("userId") String userId, HttpSession session, Model model) {
    	String sessionId = (String) session.getAttribute("userid");
    	Profile profile = profileService.getProfileInfo(userId);
    	Member userInfo = profileService.getMemberInfo(userId);
    	model.addAttribute("sessionId", sessionId);
    	model.addAttribute("userInfo",userInfo);
    	model.addAttribute("profile", profile);
    	
    	return "thymeleaf/profile/view";
    }  
    
    @GetMapping("/profile/insert")
    public String insertProfile(HttpSession session, Model model) {
    	String userId = (String) session.getAttribute("userid");
    	Member userInfo = profileService.getMemberInfo(userId);
    	model.addAttribute("userInfo", userInfo); // 세션 id에 해당하는 유저 정보를 insert form에 보내기.
    	return "thymeleaf/profile/insertform";
    }  
    
    @PostMapping("/profile/insert")
    public String insertProfile(Profile profile ,RedirectAttributes redirectAttributes) {
    	try {
    		profileService.insertProfile(profile);
        	log.info("프로필 insert 성공!");
            redirectAttributes.addFlashAttribute("message", "프로필 생성 완료");
            return "redirect:/profile/" + profile.getUserId();
        } catch (RuntimeException e) {
    		redirectAttributes.addFlashAttribute("message", e.getMessage());
    	    return "redirect:/profile/list"; 
    	}
    } 
    
    @GetMapping("/profile/update")
    public String updateProfile(@RequestParam("userId") String userId ,Model model) {
    	Member userInfo = profileService.getMemberInfo(userId);
    	model.addAttribute("userInfo", userInfo); // 세션 id에 해당하는 유저 정보를 insert form에 보내기.
    	model.addAttribute("profile", profileService.getProfileInfo(userId));
    	
    	return "thymeleaf/profile/updateform";
    } 
    
    @PostMapping("/profile/update")
	public String updateProfile(Profile profile, RedirectAttributes redirectAttributes) {
		try {
			profileService.updateProfile(profile);
			redirectAttributes.addFlashAttribute("message", profile.getUserId() + " 님 프로필이 수정되었습니다.");
		}
		catch(RuntimeException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/profile/" + profile.getUserId();
	}
}

