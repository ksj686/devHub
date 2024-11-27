package app.labs.register.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PostComment {
    private int commentId;
    private String userId;
    private int contentId;
    private String text;
    private LocalDateTime dateCreated;
    private Timestamp lastUpdated;

}
