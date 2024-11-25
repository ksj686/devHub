package app.labs.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import app.labs.content.model.Content;
import app.labs.notice.model.Notice;

@Mapper
public interface NoticeRepository {
	void createNotice(Notice notice);
	Notice getNotice(String noticeId);
	Notice getOneNotice();
	List<Notice> getAllNotice();
	List<Content> getContents();
}
