package com.tecky.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {
	
	private final UserProfileService userProfileService;

    @Autowired
	public UserProfileController(UserProfileService userProfileService) {
	
		this.userProfileService = userProfileService;
	}

	@GetMapping
	public List<UserProfile> getUserProfiles(){
		return userProfileService.getUserProfiles();
	}
	
	@PostMapping(
			path="{userProfileId}/image/upload",
			consumes=org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE,
			produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<UserProfile> uploadUserProfileImage(@PathVariable("userProfileId")UUID userProfileId
			,@RequestParam("file") MultipartFile file)
	{
		return userProfileService.uloadUserProfileImage(userProfileId,file);
	}
	
	@GetMapping(
			path="{userProfileId}/image/download")
	public byte[] downloadUserProfileImage(@PathVariable("userProfileId")UUID userProfileId)
	{
		return userProfileService.downloadUserProfileImage(userProfileId);
	}
	@GetMapping("{userProfileId}/{userProfileImgLink}/download")
	public byte[] downloadUserProfileImgWithLink(@PathVariable("userProfileId") UUID userProfileId, @PathVariable("userProfileImgLink") String userProfileImgLink) {
	
		return userProfileService.downloadUserProfileImgWithLink(userProfileId, userProfileImgLink);
		

	}
}
