package app.labs.register.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MyContent {
	private int contentId;
	private String userId;
	private String title;
	private Timestamp dateCreated;
	private Timestamp lastUpdated;
	private int fileId;
	private int recommendCnt;
	private String text;
 }
