package app.labs.content.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Content {
	private int contentId;
	private String userId;
	private String title;
	private LocalDateTime dateCreated;
	private LocalDateTime lastUpdated;
	private int fileId;
	private int recommendCnt;
	private String text;
 }
