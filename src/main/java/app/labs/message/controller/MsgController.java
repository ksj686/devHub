package app.labs.message.controller;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j 
// 여기서 @Slf4j 는 sysout대신 log.info 로 원하는 로그 출력 도와주는 역할. 
// 실제 log 설정은 application.properties에 들어있음. 
// debug 설정은 mybatis 쿼리를 출력해준다.
public class MsgController {

    @Autowired
    private MsgService msgService;

    // 보낸 메시지 조회
//    @GetMapping("/message/sent/{senderId}")
    @GetMapping("/message/sent")
    public String getSentMessages(
//    		@PathVariable("senderId") String senderId, 
    		Model model, HttpSession session) {
    	String userId = (String) session.getAttribute("userid");
        List<Msg> sentMessages = msgService.getSentMessages(userId);
        model.addAttribute("sentMessages", sentMessages);
        log.info("getSentMessages 메소드 실행");
        return "thymeleaf/message/sent";
    }
    
//  보낸 메시지 검색
    @GetMapping("/message/sent/search")
    public String sentMsgSearch(@RequestParam("filter") String filter,
    								@RequestParam("query") String query,
    				Model model, HttpSession session) {
    	log.info("messageSearch 메서드 : "+ filter);
    	String userId = (String) session.getAttribute("userid");
	    String searchFilter = URLDecoder.decode(filter, StandardCharsets.UTF_8);
	    String searchQuery = URLDecoder.decode(query, StandardCharsets.UTF_8);
	    log.info("userId : "+userId + " searchFilter : " + searchFilter + " searchQuery : " + searchQuery);
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("searchFilter", searchFilter);
	    params.put("searchQuery", searchQuery);

//    	List<Msg> sentMessages = msgService.sentMsgSearch(userId, searchFilter, searchQuery);
    	List<Msg> sentMessages = msgService.sentMsgSearch(params);
    	model.addAttribute("sentMessages", sentMessages);

    	return "thymeleaf/message/sent";  // 받은 메시지 상세 페이지
	}
    
//  받은 메시지 검색
    @GetMapping("/message/received/search")
    public String receivedMsgSearch(@RequestParam("filter") String filter,
    								@RequestParam("query") String query,
    				Model model, HttpSession session) {
    	log.info("messageSearch 메서드 : "+ filter);
    	String userId = (String) session.getAttribute("userid");
	    String searchFilter = URLDecoder.decode(filter, StandardCharsets.UTF_8);
	    String searchQuery = URLDecoder.decode(query, StandardCharsets.UTF_8);
	    log.info("userId : "+userId + " searchFilter : " + searchFilter + " searchQuery : " + searchQuery);
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("searchFilter", searchFilter);
	    params.put("searchQuery", searchQuery);

//    	List<Msg> sentMessages = msgService.sentMsgSearch(userId, searchFilter, searchQuery);
    	List<Msg> receivedMessages = msgService.receivedMsgSearch(params);
    	model.addAttribute("receivedMessages", receivedMessages);

    	return "thymeleaf/message/received";  // 받은 메시지 상세 페이지
	}

    
    @PostMapping("/message/insert")
    public String insertMsg(Msg msg,/* @RequestParam(value = "receiverId") String receiverId, @RequestParam(value = "text") String text, */ HttpSession session, RedirectAttributes redirectAttributes) {
    	log.info("insertMsg 메소드 실행");
    	String userId = (String) session.getAttribute("userid");
    	
//    	String uniqueKey = UUID.randomUUID().toString(); // import java.util.UUID;
    	
    	long currentTimeMillis = System.currentTimeMillis();
    	int randomValue = (int) (Math.random() * 1000);  // 0~999 사이의 랜덤 값
    	String uniqueKey = currentTimeMillis + "-" + randomValue;
    	msg.setMessageId(uniqueKey);
//    	log.info("receiver id ? : " + msg.getReceiverId());
//    	msg.setReceiverId(receiverId);
//    	msg.setText(text);
    	msg.setSenderId(userId);
		try {
			int num = msgService.insertMsg(msg);
			log.info(num + "개 쿼리 실행 완료.");
			redirectAttributes.addFlashAttribute("message", 
					"메시지를 보냈습니다.");
		}
		catch(RuntimeException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/message/sent";
//		return "저장완료";
	}

    // 받은 메시지 조회
//    @GetMapping("/message/received/{receiverId}")
    @GetMapping("/message/received")
	public String getReceivedMessages(
//			@PathVariable("receiverId") String receiverId,
			Model model, HttpSession session) {
    	String userId = (String) session.getAttribute("userid");
        List<Msg> receivedMessages = msgService.getReceivedMessages(userId);
        model.addAttribute("receivedMessages", receivedMessages);
        return "thymeleaf/message/received";
    }
    
    // 보낸 메시지 상세 조회
//    @GetMapping("/message/sent/detail/{messageId}/{viewerId}")
    @GetMapping("/message/sent/detail/{messageId}")
    public String getSentMsgDetail(@PathVariable("messageId") String messageId,
//                                   @PathVariable("viewerId") String viewerId, 
                                   Model model, HttpSession session) {
										 
        String userId = (String) session.getAttribute("userid");
        Msg msg = msgService.getSentMsgDetail(messageId, userId); // 보낸 메시지 상세 조회
        model.addAttribute("msg", msg);
        return "thymeleaf/message/sentmsgdetail";  // 보낸 메시지 상세 페이지
    }
	

    // 받은 메시지 상세 조회
//    @GetMapping("/message/received/detail/{messageId}/{viewerId}")
    @GetMapping("/message/received/detail/{messageId}")
    public String getReceivedMsgDetail(
    					@PathVariable("messageId") String messageId,
//    					@PathVariable("viewerId") String viewerId, 
    					Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        
        Msg msg = msgService.getReceivedMsgDetail(messageId, userId); // 받은 메시지 상세 조회
        model.addAttribute("msg", msg);
        return "thymeleaf/message/receivedmsgdetail";  // 받은 메시지 상세 페이지
    }
    
//    메시지 보내기 팝업창
    @GetMapping("/message/popupsend")
    public String sendPopup(
    					Model model, HttpSession session) {
        return "thymeleaf/message/popupsend";  // 받은 메시지 상세 페이지
    }

    //보낸 메시지 삭제
    @PostMapping("/message/deletesent")
    public String deleteSent(@RequestParam("messageIds") List<String> messageIds,
				Model model, HttpSession session) {
    	log.info(messageIds.get(0));
    	int cnt = msgService.deleteSent(messageIds);
    	log.info(cnt+"건의 보낸 메시지 삭제");
    	return "redirect:/message/sent";
	}
    
    //받은 메시지 삭제
    @PostMapping("/message/deletereceived")
    public String deleteReceived(@RequestParam("messageIds") List<String> messageIds,
				Model model, HttpSession session) {
    	int cnt = msgService.deleteReceived(messageIds);
    	log.info(cnt+"건의 받은 메시지 삭제");
    	return "redirect:/message/received";
	}
}
