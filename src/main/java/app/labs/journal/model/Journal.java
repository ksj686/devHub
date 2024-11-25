package app.labs.journal.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Journal {
	private String journalId;
	private String userId;
	private String title;
	private String filePath;  // HTML 텍스트 (이미지 태그 포함)
	private Timestamp dateCreated;
	private Timestamp lastUpdated;
}