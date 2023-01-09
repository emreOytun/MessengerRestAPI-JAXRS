package com.emreoytun.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emreoytun.messenger.dao.DatabaseClass;
import com.emreoytun.messenger.entity.Profile;

public class ProfileService {
	
	private final Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values()); 
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		if (profiles.containsKey(profile.getProfileName())) {
			return null;
		}
		
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if (profile.getId() <= 0) {
			return null;
		}
		
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
	
	
	
}
