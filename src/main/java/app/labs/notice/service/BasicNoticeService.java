package app.labs.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.content.model.Content;
import app.labs.notice.dao.NoticeRepository;
import app.labs.notice.model.Notice;

@Service
public class BasicNoticeService implements NoticeService{

	@Autowired
	private NoticeRepository noticeRepository;
	@Override
	public void createNotice(Notice notice) {
		noticeRepository.createNotice(notice);
	}
	@Override
	public Notice getNotice(String noticeId) {
		return noticeRepository.getNotice(noticeId);
	}
	@Override
	public List<Notice> getAllNotice() {
		return noticeRepository.getAllNotice();
	}
	
	@Override
	public Notice getOneNotice() {
		return noticeRepository.getOneNotice();
	}
	
	@Override
	public List<Content> getContents() {
		return noticeRepository.getContents();
	}
}