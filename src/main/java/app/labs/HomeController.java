package app.labs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		 return "home";
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