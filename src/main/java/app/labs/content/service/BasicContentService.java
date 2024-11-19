package app.labs.content.service;

import java.util.List;
import java.time.LocalDateTime;

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
        content.setDateCreated(LocalDateTime.now());
        content.setLastUpdated(LocalDateTime.now());
		contentRepository.createContent(content);
	}

	@Override
	public void editContent(Content content) {
        content.setLastUpdated(LocalDateTime.now());
		contentRepository.editContent(content);
	}

	@Override
	public void deleteContent(int contentId, String userId) {
		contentRepository.deleteContent(contentId, userId);
	}

	@Override
	public List<Content> getAllContents() {
		return contentRepository.getAllContents();
	}
	
//    @Override
//    public void increaseRecommend (int contentId) {
//        contentRepository.increaseRecommend (contentId); // DB에서 추천 수 증가
//    }
//    
//    @Override
//    public int getRecommendCount(int contentId) {
//        return contentRepository.getRecommendCount(contentId); // DB에서 추천 수 조회
//    }

}
