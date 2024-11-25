package app.labs.profile.service;

import java.util.List;
import java.util.Map;
import app.labs.profile.model.Profile;
import app.labs.register.model.Member;

public interface ProfileService {
	
	List<Profile> getProfileList();

	// 특정 유저의 프로필 정보 불러오기
	Profile getProfileInfo(String userId);

	// 프로필 insert
	void insertProfile(Profile profile);
	
	// 프로필 업데이트 메소드
	void updateProfile(Profile profile);
		
	// 코스
//	Profile getCourseName(String courseId);

	Member getMemberInfo(String userId);
    
}

