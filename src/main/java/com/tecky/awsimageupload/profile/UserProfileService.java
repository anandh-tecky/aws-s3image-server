package com.tecky.awsimageupload.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tecky.awsimageupload.bucket.BucketName;
import com.tecky.awsimageupload.filehandling.FileStore;

@Service
public class UserProfileService {
	
	private final UserProfileDataAccessService userProfileDataAccessService;
	private final FileStore fileStore;

	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
			FileStore fileStore) {
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}
	
	List<UserProfile> getUserProfiles(){
		return userProfileDataAccessService.getUserProfiles();
	}

	public void uloadUserProfileImage(UUID userProfileId, MultipartFile file) {
		//1.check if file is not empty
		isFileEmpty(file);
		//2.if file is image
		isFileImage(file);
		//3.The user exist in our database
		UserProfile user=getUserProfileOrThrow(userProfileId);
		
		//4.Grab some metadata from file
		Map<String,String> metadata=new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length",String.valueOf(file.getSize()));
		
		//5.store the image in s3 and update database(userProfileImageLink)with s3 link
		String path=String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),user.getUserProfileId());
		String filename=String.format("%s-%s", file.getOriginalFilename(),UUID.randomUUID());
		try {
			fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
			user.setUserProfileImageLink(filename);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		
		
	}
	
	public byte[] downloadUserProfileImage(UUID userProfileId) {
		UserProfile user=getUserProfileOrThrow(userProfileId);
		String path=String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),
				user.getUserProfileId());
		return user.getUserProfileImageLink()
			.map(key->fileStore.download(path,key))
			.orElse(new byte[0]);
	}

	private UserProfile getUserProfileOrThrow(UUID userProfileId) {
		return userProfileDataAccessService
		.getUserProfiles()
		.stream()
		.filter(userProfile->userProfile.getUserProfileId().equals(userProfileId))
		.findFirst()
		.orElseThrow(()->new IllegalStateException(String.format("User profile %s not found", userProfileId)));
	}

	private void isFileImage(MultipartFile file) {
		if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType()
				,ContentType.IMAGE_PNG.getMimeType()
				,ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType()))
		{
			throw new IllegalStateException("File must be an image");
		}
	}

	private void isFileEmpty(MultipartFile file) {
		if(file.isEmpty())
		{
			throw new IllegalStateException("Cannot ulpoad empty file["+file.getSize()+"]");
		}
	}

	
	
	

}
