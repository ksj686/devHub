package app.labs.profile.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import app.labs.profile.model.Profile;
import app.labs.register.model.Member;

@Mapper
public interface ProfileRepository {
	// 모든 프로필 리스트
	List<Profile> getProfileList();
	
	// 특정 유저의 프로필 불러오기
	Profile getProfileInfo(String userId);
	
	Profile getProfileImage(int imageId);
		
	// 프로필 업데이트 메소드
	void updateProfile(Profile profile);
				
	// 프로필 insert
	void insertProfile(Profile profile);
	
	Member getMemberInfo(String userId);
	
	void updateProfileImage(Profile profile);
	
	int getMaxImageId();
}

