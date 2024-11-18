// ContentController.java
package app.labs.content.controller;

import app.labs.content.model.Content;
import app.labs.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public String getAllContents(Model model) {
        List<Content> contents = contentService.getAllContents();
        model.addAttribute("contents", contents);
        return "thymeleaf/content/contents";
    }

    @GetMapping("/{contentId}")
    public String getContentDetails(@PathVariable int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId);
        model.addAttribute("content", content);
        return "thymeleaf/content/contents_details";
    }

    @GetMapping("/new")
    public String createContentForm(Model model) {
        model.addAttribute("content", new Content());
        return "thymeleaf/content/contents_form";
    }

    @PostMapping
    public String createContent(Content content) {
        contentService.createContent(content);
        return "redirect:/contents";
    }

    @GetMapping("/edit/{contentId}")
    public String editContentForm(@PathVariable int contentId, Model model) {
        Content content = contentService.getContentInfo(contentId);
        model.addAttribute("content", content);
        return "thymeleaf/content/contents_form";
    }

    @PostMapping("/edit/{contentId}")
    public String editContent(@PathVariable int contentId, Content content) {
        content.setContentId(contentId);
        contentService.editContent(content);
        return "redirect:/contents";
    }

    @DeleteMapping("/{contentId}")
    @ResponseBody
    public String deleteContent(@PathVariable int contentId) {
        contentService.deleteContent(contentId);
        return "Content deleted successfully.";
    }
}
