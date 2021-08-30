package com.tecky.awsimageupload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	@Value("${AWS_ACCESS_KEY_ID}")
	private String accessKey;
	
	@Value("${AWS_SECRET_ACCESS_KEY}")
	private String secretKey;
	

	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials=new BasicAWSCredentials(this.accessKey, this.secretKey);
		return AmazonS3ClientBuilder
				.standard()
				.withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}
}
