package app.labs.profile.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    
    //이미지 가져오기
    @GetMapping("/profile/image/{imageId}")
    public ResponseEntity<byte[]> getBinaryFile(@PathVariable("imageId") int imageId){
    	final HttpHeaders headers = new HttpHeaders();
    	
    	Profile image = profileService.getProfileImage(imageId);
    	
    	log.info("get Image" + image.toString());
    	String[] mtypes = image.getImageContentType().split("/");
    	headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
    	headers.setContentDispositionFormData("ataachment", image.getImageName());
    	return new ResponseEntity<byte[]>(image.getImageData(), headers, HttpStatus.OK);
    }
   
    
    @GetMapping("/profile/insert")
    public String insertProfile(HttpSession session, Model model) {
    	String userId = (String) session.getAttribute("userid");
    	Member userInfo = profileService.getMemberInfo(userId);
    	
    	// 기본 profile 객체 생성 및 초기화
    	Profile profile = new Profile();
    	profile.setUserId(userId);
    	
    	model.addAttribute("userInfo", userInfo); // 세션 id에 해당하는 유저 정보를 insert form에 보내기.
    	return "thymeleaf/profile/insertform";
    }  
    
    @PostMapping("/profile/insert")
    public String insertProfile(Profile profile ,RedirectAttributes redirectAttributes,
    							@RequestParam("image") MultipartFile image) {
    	try {
    		if(image != null && !image.isEmpty()) {
    			profile.setImageName(image.getOriginalFilename());
    			profile.setImageData(image.getBytes());
    			profile.setImageContentType(image.getContentType());
    			log.info(image.getOriginalFilename());
    			
    			profileService.insertProfile(profile);
    			
            	log.info("프로필 insert 성공!");
                redirectAttributes.addFlashAttribute("message", "프로필 생성 완료");
                return "redirect:/profile/" + profile.getUserId();
    		}  		
        } catch (Exception e) {
    		redirectAttributes.addFlashAttribute("message", e.getMessage());
    	    return "redirect:/profile/list"; 
    	}
		return "redirect:/profile/list";
    } 
    
    @GetMapping("/profile/update")
    public String updateProfile(@RequestParam("userId") String userId ,Model model) {
    	Member userInfo = profileService.getMemberInfo(userId);
    	Profile profile = profileService.getProfileInfo(userId);
    	model.addAttribute("userInfo", userInfo); // 세션 id에 해당하는 유저 정보를 insert form에 보내기.
    	model.addAttribute("profile", profile);
    	
    	return "thymeleaf/profile/updateform";
    } 
    
    @PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute Profile profile,
            					@RequestParam(value="image", required = false) MultipartFile image,
            					@RequestParam(value="imageId2", required = false, defaultValue="-1") int imageId2,
            					RedirectAttributes redirectAttributes) {
    	log.info(imageId2 + "해당 프로필 이미지 아이디");
		try {			
			if (image != null && !image.isEmpty()) {
				profile.setImageId(imageId2);
				
				profile.setImageName(image.getOriginalFilename());
    			profile.setImageData(image.getBytes());
    			profile.setImageContentType(image.getContentType());
    			
//    			int imageId = profile.getImageId();
    			profileService.updateProfileImage(profile);
	            
	        }
			profileService.updateProfile(profile);
			redirectAttributes.addFlashAttribute("message", profile.getUserId() + " 님 프로필이 수정되었습니다.");
		}
		catch(RuntimeException | IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/profile/" + profile.getUserId();
	}

}

