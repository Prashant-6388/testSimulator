package com.pc.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private static final Logger log = LoggerFactory.getLogger(S3Service.class);
	
	private static final String PROFILE_PICTURE_FILENAME = "profilePicture";
	
	@Value("${aws.s3.root.bucket.name}")
	private String bucketName;
	
	@Value("${aws.s3.profile}")
	private String awsProfile;
	
	@Value("${aws.storage.temp.folder}")
	private String tempFolder;
	
	@Autowired 
	AmazonS3 amazonS3;
	
	public List<Bucket> getS3BucketList() {
		return amazonS3.listBuckets();
	}
	
	//not working : do not use 
	public boolean checkIfBucketExists(String bucketName) {
		boolean doesBucketExistV2 = amazonS3.doesBucketExistV2(bucketName);
		System.out.println("Bucket exists : "+doesBucketExistV2);
		return false;
	}
	
	
	public String storeProfileImage(MultipartFile uploadedFile,String username) throws IOException {
		String profileImageURL =null;
		
		if(uploadedFile!=null && !uploadedFile.isEmpty()) {
			byte[] bytes = uploadedFile.getBytes();
			
			File tempStorageFolder = new File(tempFolder + File.separator + username);
			if(!tempStorageFolder.exists()) {
				log.debug("Creating temp root for S3 assets");
				tempStorageFolder.mkdirs();
			}
			
			File tempImageFile = new File(tempStorageFolder.getAbsolutePath()
					+ File.pathSeparatorChar
					+ PROFILE_PICTURE_FILENAME
					+ "."
					+ FilenameUtils.getExtension(uploadedFile.getOriginalFilename()));
			
			log.info("temporary image will be stored to {}",tempImageFile);
			
			try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(tempImageFile.getAbsolutePath()))){
				stream.write(bytes);
			}
			
			profileImageURL =this.storeProfileImageToS3(tempImageFile, username);
			tempImageFile.delete();
		}
		
		return profileImageURL;
	}
	
	public String ensureBucketExists(String bucketName) {
		String bucketUrl = null;
		try {
			if(!amazonS3.doesBucketExistV2(bucketName)) {
				log.info("Bucket {} does not exists",bucketName);
				amazonS3.createBucket(bucketName);
				log.info("Bucket created with name {}",bucketName);
			}
			bucketUrl = amazonS3.getUrl(bucketName, null) + bucketName;
		} catch(AmazonClientException ace) {
			log.error("Error checking bucket : {}", ace.getMessage());
		}
		return bucketUrl;
	}
	
	public String storeProfileImageToS3(File resource, String username) {
		URL resourceUrl = null;
		
		if(!resource.exists()) {
			log.error("The file {} does not exists",resource.getAbsolutePath());
		}
		
		String rootUrl = ensureBucketExists(bucketName);
		
		if(null == rootUrl) {
			log.error(
					"Bucket {} does not exists and application failed to create bucket, image will not be stored with profile",
					bucketName);
		} else {
			AccessControlList acl = new AccessControlList();
			acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
			
			String key = username + "/" + PROFILE_PICTURE_FILENAME + "." + FilenameUtils.getExtension(resource.getName());
			
			try {
				amazonS3.putObject(new PutObjectRequest(bucketName, key, resource));
				resourceUrl = amazonS3.getUrl(bucketName, key);
			}catch(AmazonClientException ace) {
				log.error("Error stroring profile image to S3 bucket {} with key {}",bucketName, key);
			}
		}
		return resourceUrl.toString();
	}
}
