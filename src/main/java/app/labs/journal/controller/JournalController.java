package app.labs.journal.controller;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.labs.journal.model.Journal;
import app.labs.journal.service.JournalService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class JournalController {

    @Autowired
    private JournalService journalService;

    @GetMapping("/journal_insert")
    public String submitJournal(Journal journal, HttpSession session) {
		return "thymeleaf/journal/journal_insert";
	}
    
    @PostMapping("/submit_journal")
    @ResponseBody
    public ResponseEntity<Map<String, String>> submitJournal(
    							@RequestParam("uniqueId") String uniqueId, 
    							@RequestParam("content") String content, 
    							Journal journal,
    							HttpSession session, RedirectAttributes redirectAttributes) {
    	Map<String, String> response = new HashMap<>();
    	log.info(journal.getTitle());
    	log.info(uniqueId);
    	journal.setUserId((String)session.getAttribute("userid"));
    	String filePath = "src/main/resources/static/upload/" + uniqueId + "_" + System.currentTimeMillis();
    	journal.setFilePath(filePath);
    	File file = new File(filePath);
    	try (FileWriter writer = new FileWriter(file)) { 
    		log.info("try write");
    		writer.write(content); 
    		log.info("write done");
        	journalService.createJournal(journal);
        	log.info("insert done");
        	response.put("status", "OK"); 
        	return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "ERROR"); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/journals")
    public String getJournal(
//    					@PathVariable String fileName, 
    					HttpSession session, Model model) {
    	String userId = (String) session.getAttribute("userid");
    	List<Journal> journals = journalService.getAllJournal(userId);
    	// 파일 경로로 파일 불러오기 추가
        model.addAttribute("boardJournal", journals);
		return "thymeleaf/journal/journals";
    }
    
    @GetMapping("/journal/{journalId}")
    public String getJournalDetail(
    					@PathVariable("journalId") String journalId, 
    					Model model, Journal journal) {
    	int journalId_ = Integer.parseInt(journalId);
    	log.info(journalId);
    	journal = journalService.getJournal(journalId_);
    	// 파일 경로로 파일 불러오기 추가
    	try {
	    	String filePath = journal.getFilePath();
	    	String content = new String(Files.readAllBytes(Paths.get(filePath)));
	        model.addAttribute("boardJournal", journal);
//	        model.addAttribute("content", content.replace("<br>", "\n"));
	        model.addAttribute("content", content);
	        return "thymeleaf/journal/journal_select";
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return "";
		}
    }
}