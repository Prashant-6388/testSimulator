package com.pc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

@Service
public class S3Service {

	@Autowired 
	AmazonS3 amazonS3;
	
	public List<Bucket> getS3BucketList() {
		return amazonS3.listBuckets();
	}
	
	public boolean checkIfBucketExists(String bucketName) {
		
		boolean doesBucketExistV2 = amazonS3.doesBucketExistV2(bucketName);
		System.out.println("Bucket exists : "+doesBucketExistV2);
		return false;
	}
}
