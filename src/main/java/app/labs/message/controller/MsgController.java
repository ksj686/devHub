package app.labs.message.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.message.model.Msg;
import app.labs.message.service.MsgService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MsgController {

    @Autowired
    private MsgService msgService;

    // 보낸 메시지 조회
    @GetMapping("/message/sent/{senderId}")
    public String getSentMessages(@PathVariable("senderId") String senderId, Model model) {
        List<Msg> sentMessages = msgService.getSentMessages(senderId);
        model.addAttribute("sentMessages", sentMessages);
        return "thymeleaf/message/sent";
    }

    // 받은 메시지 조회
    @GetMapping("/message/received/{receiverId}")
    public String getReceivedMessages(@PathVariable("receiverId") String receiverId, Model model) {
        List<Msg> receivedMessages = msgService.getReceivedMessages(receiverId);
        model.addAttribute("receivedMessages", receivedMessages);
        return "thymeleaf/message/received";
    }
    
    // 메시지 상세 조회
    @GetMapping("/message/{messageId}/{viewerId}")
    public String getMsgDetail(
    		@PathVariable("messageId") int messageId,
    		@PathVariable("viewerId") String viewerId, 
    		Model model, HttpSession session) {
    	String userId = (String) session.getAttribute("userid");
        Msg msg = msgService.getMsgDetail(messageId, userId);	// 조회자가 수신자인지 확인 후 수신 날짜 update
        model.addAttribute("msg", msg);
        return "thymeleaf/message/msgdetail";  // Thymeleaf 템플릿을 반환
    }
    

    // 추후 삭제예정
//    @GetMapping("/message/{userId}")
//	public String loginEmp(@PathVariable("userId") String userId, 
//			               HttpSession session, RedirectAttributes redirectAttrs) {	
//		
//				
//		session.setMaxInactiveInterval(600); //10분
//		session.setAttribute("userid", userId);
//		
//		return "redirect:/message/sent/"+userId;
//		
//	}
    
    // 메시지 읽음 처리
//    @GetMapping("/message/read/{messageId}")
//    public String markMessageAsRead(@PathVariable("messageId") int messageId) {
//        msgService.updateMessageReadStatus(messageId);
//        return "redirect:thymeleaf/message/received/{receiverId}";
//    }
}
