package app.labs.profile.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.labs.profile.dao.ProfileRepository;
import app.labs.profile.model.Profile;
import app.labs.register.model.Member;

@Service
public class BasicProfileService implements ProfileService {
	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public Profile getProfileInfo(String userId) {
		return profileRepository.getProfileInfo(userId);
	}

	@Override
	public void insertProfile(Profile profile) throws IOException { 
		int newImageId = profileRepository.getMaxImageId() + 1;
		profile.setImageId(newImageId);
		profileRepository.insertProfile(profile);
	}	
	@Override
	public void updateProfile(Profile profile) {
		profileRepository.updateProfile(profile);
	}

	@Override
	public List<Profile> getProfileList() {
		return profileRepository.getProfileList();
	}

	@Override
	public Member getMemberInfo(String userId) {
		return profileRepository.getMemberInfo(userId);
	}

	@Override
	public Profile getProfileImage(int imageId){
		
		return profileRepository.getProfileImage(imageId);
	}
	
	@Override
	public void updateProfileImage(Profile profile) throws IOException {
		
//		Profile profile = new Profile();
//		profile.setImageData(file.getBytes());
//		profile.setUserId(userId);
//		profile.setImageId(newImageId);
//		byte[] imageData = file.getBytes();
//		profileRepository.updateProfileImage(userId, imageData);
		profileRepository.updateProfileImage(profile); 
	}



	
}
