package app.labs.profile.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import app.labs.profile.model.Profile;
import app.labs.register.model.Member;

public interface ProfileService {
	
	List<Profile> getProfileList();

	// 특정 유저의 프로필 정보 불러오기
	Profile getProfileInfo(String userId);

	Profile getProfileImage(int imageId);

	// 프로필 insert
	void insertProfile(Profile profile) throws IOException;
	
	// 프로필 업데이트 메소드
	void updateProfile(Profile profile);

	Member getMemberInfo(String userId);
	
	void updateProfileImage(Profile profile) throws IOException; 
    
}

