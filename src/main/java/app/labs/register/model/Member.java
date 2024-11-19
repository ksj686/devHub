package app.labs.register.model;


import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Member {
    private Long idx;
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String phoneNumber;
    private String userStatus;
    private Date lastLogin;
    

    // Getters and Setters
}

