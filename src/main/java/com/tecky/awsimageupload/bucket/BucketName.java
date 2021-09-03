package com.tecky.awsimageupload.bucket;

public enum BucketName {
	

	PROFILE_IMAGE("tecky-image-upload");

	private final String bucketName;
	
	BucketName(String bucketName) {
		this.bucketName=bucketName;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	
	
	
}
