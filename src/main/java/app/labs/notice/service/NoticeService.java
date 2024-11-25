package app.labs.notice.service;

import java.util.List;

import app.labs.content.model.Content;
import app.labs.notice.model.Notice;

public interface NoticeService {
	void createNotice(Notice notice);
	Notice getNotice(String noticeId);
	Notice getOneNotice();
	List<Notice> getAllNotice();
	List<Content> getContents();
}
