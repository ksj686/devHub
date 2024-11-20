package app.labs.content.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Content {
	private int contentId;
	private String userId;
	private String title;
	private Timestamp dateCreated;
	private Timestamp lastUpdated;
	private int fileId;
	private int recommendCnt;
	private String text;
	
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = new Timestamp(dateCreated.getTime() / 1000 * 1000); // 밀리초 제거
    }
    
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = new Timestamp(lastUpdated.getTime() / 1000 * 1000);
    }

 }

