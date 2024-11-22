package app.labs.content.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Comment {
    private int commentId;
    private String userId;
    private int contentId;
    private String text;
    private Timestamp dateCreated;
    private Timestamp lastUpdated;

}
