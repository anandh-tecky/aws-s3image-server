package com.tecky.awsimageupload.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.tecky.awsimageupload.profile.UserProfile;

@Repository
public class FakeUserProfileDataStore {

	private static final List<UserProfile> USER_PROFILES=new ArrayList<>();
	
	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("4c13d7d1-b98e-49ae-a079-3671f10f5eed"),"Anandh",null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("7e145433-12dd-4b80-8d94-6926c2db00e3"),"Chris",null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("6e145433-12dd-4b80-8d94-4926c2db00e3"),"John",null));
	}
	
	public List<UserProfile> getUserProfiles(){
		return USER_PROFILES;
	}
}
