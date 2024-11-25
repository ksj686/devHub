package app.labs.profile.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString //(exclude= {"profileImageData", "backgroundImageData"})

public class Profile {
	private int profileId;
    private String userId;
    private String userName;
    private String email;
    private String courseId;
    private String biography;
    
//    private String courseName;
//    private byte[] profileImageData;
//    private String profileImageName;
//    private long profileImageSize;
//    private byte[] backgroundImageData;
//    private String backgroundImageName;
//    private long backgroundImageSize;
    
//    private String lastUpdated;
 
}
