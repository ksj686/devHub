package app.labs.profile.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.profile.dao.ProfileRepository;
import app.labs.profile.model.Profile;

@Service
public class BasicProfileService implements ProfileService {
	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public Profile getProfileInfo(String userId) {
		return profileRepository.getProfileInfo(userId);
	}

	@Override
	public void insertProfile(Profile profile) {
		profileRepository.insertProfile(profile);
	}	
	@Override
	public void updateProfile(Profile profile) {
		profileRepository.updateProfile(profile);
	}

	@Override
	public List<Map<String, Object>> getAllCourseId() {
		return profileRepository.getAllCourseId();
	}

	@Override
	public List<Profile> getProfileList() {
		return profileRepository.getProfileList();
	}

//	@Override
//	public int getMaxProfileImageId() {
//		return 0;
//	}
//
//	@Override
//	public int getMaxBackgroundImageId() {
//		return 0;
//	}
//
//	@Override
//	public List<Profile> getProfileImageList() {
//		return null;
//	}
//
//	@Override
//	public List<Profile> getAllProfileImageList() {
//		return null;
//	}
//
//	@Override
//	public List<Profile> getBackgroundImageList() {
//		return null;
//	}
//
//	@Override
//	public List<Profile> getAllBackgroundImageList() {
//		return null;
//	}
//
//	@Override
//	public Profile getProfileImage(int profileImageId) {
//		return null;
//	}
//
//	@Override
//	public Profile getBackgroundImage(int backgroundImageId) {
//		return null;
//	}

    
}
