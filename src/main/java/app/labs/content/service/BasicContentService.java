package app.labs.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.content.dao.ContentRepository;
import app.labs.content.model.Content;

@Service
public class BasicContentService implements ContentService {

	@Autowired
	private ContentRepository contentRepository;
	
	@Override
	public Content getContentInfo(int contentId) {
		return contentRepository.getContentInfo(contentId);
	}

	@Override
	public void createContent(Content content) {
		contentRepository.createContent(content);
	}

	@Override
	public void editContent(Content content) {
		contentRepository.editContent(content);
	}

	@Override
	public void deleteContent(int contentId) {
		contentRepository.deleteContent(contentId);
	}

	@Override
	public List<Content> getAllContents() {
		return contentRepository.getAllContents();
	}

}
