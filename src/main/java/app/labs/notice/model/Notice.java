package app.labs.notice.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Notice {
	private String noticeId;
	private String userId;
	private String title;
	private String text;  // HTML 텍스트 (이미지 태그 포함)
	private Timestamp dateCreated;
	private Timestamp lastUpdated;
	private int views;
}