package app.labs.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Msg {

    private int messageId;
    private String receiverId;
    private String senderId;
    private String text;
    private String dateCreated;
    private String dateRead;
}

