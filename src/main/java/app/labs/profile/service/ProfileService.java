package app.labs.profile.service;

import java.util.List;
import java.util.Map;
import app.labs.profile.model.Profile;

public interface ProfileService {
	
	List<Profile> getProfileList();

	// 특정 유저의 프로필 정보 불러오기
	Profile getProfileInfo(String userId);

	// 프로필 insert
	void insertProfile(Profile profile);
	
	// 프로필 업데이트 메소드
	void updateProfile(Profile profile);
		
	// 코스
	List<Map<String, Object>> getAllCourseId();
		
	// 멤버
//	List<Map<String, Object>> getAllUserId();
				
	// 프로필 이미지 아이디
//	int getMaxProfileImageId();
		
	// 배경 이미지 아이디
//	int getMaxBackgroundImageId();
			
	// 프로필 이미지 리스트 생성
//	List<Profile> getProfileImageList();
//	List<Profile> getAllProfileImageList();

	// 프로필 배경 이미지 리스트 생성
//	List<Profile> getBackgroundImageList();
//	List<Profile> getAllBackgroundImageList();
		
	// 이미지 파일을 가져오는 것
//	Profile getProfileImage(int profileImageId);
//	Profile getBackgroundImage(int backgroundImageId);
    
}

