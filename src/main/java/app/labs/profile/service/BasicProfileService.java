package app.labs.profile.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void insertProfile(Profile profile) {
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
}
