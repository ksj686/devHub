package app.labs.content.service;

import java.util.List;
import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;
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
        content.setDateCreated(new Timestamp(System.currentTimeMillis()));
        content.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		contentRepository.createContent(content);
	}

	@Override
	public void editContent(Content content) {
        content.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		contentRepository.editContent(content);
	}

	@Override
    public boolean deleteContent(int contentId, String enteredUserId) {
        Content content = contentRepository.getContentInfo(contentId); // 글 정보 조회
      
        // 글의 userId와 입력받은 userId 비교
        if (content != null && content.getUserId().equals(enteredUserId)) { 
            contentRepository.deleteContent(contentId); // 삭제 수행
            return true; // 삭제 성공
        }
        return false; // 삭제 실패: userId 불일치
    }

	@Override
	public List<Content> getAllContents() {
		return contentRepository.getAllContents();
	}
	
    @Override
    public void increaseRecommend (int contentId) {
        contentRepository.increaseRecommend (contentId); // DB에서 추천 수 증가
    }
    
    @Override
    public int getRecommendCount(int contentId) {
        return contentRepository.getRecommendCount(contentId); // DB에서 추천 수 조회
    }
	
    @Override
    public List<Content> getPagedContents(@Param("offset") int page, @Param("offset") int size) {
        int offset = (page - 1) * size; // 시작 위치 계산
        return contentRepository.getPagedContents(offset, size);
    }

    @Override
    public int getTotalContentCount() {
        return contentRepository.getTotalContentCount();
    }
    
    public boolean recommendContent(@Param("contentId")int contentId, @Param("userId") String userId) {
        // 추천 이력 확인
        int recommendCount = contentRepository.getRecommendCountByUserAndContent(contentId, userId);

        if (recommendCount > 0) {
            // 이미 추천한 경우
            return false;
        }
        // 추천 기록 추가
       contentRepository.addRecommendHistory(contentId, userId);
        // 콘텐츠 추천 수 증가
        contentRepository.increaseRecommend(contentId);

        return true;
    }
    
    public List<Content> searchByTitle(String title) {
        return contentRepository.findByTitleContaining(title);
    }

    public List<Content> searchByUserId(String userId) {
        return contentRepository.findByUserIdContaining(userId);
    }

}
