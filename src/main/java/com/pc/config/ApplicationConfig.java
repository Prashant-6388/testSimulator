package com.pc.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by tedonema on 30/03/2016.
 */
@Configuration 
@EnableJpaRepositories(basePackages = "com.pc.persistance")
@EntityScan(basePackages = "com.pc.model")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.testsim/application-common.properties")
//@PropertySource("file:///${user.home}/.testsim/stripe.properties")
public class ApplicationConfig {

    @Value("${aws.s3.profile}")
    private String awsProfileName;

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new ProfileCredentialsProvider(awsProfileName).getCredentials();
        AWSCredentialsProvider credentialProvider = new AWSStaticCredentialsProvider(credentials);
//        AmazonS3ClientBuilder s3Clientbuilder = AmazonS3ClientBuilder.standard().withCredentials(credentialProvider);
        return  AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
//        return amazonS3Client;
        
    }
}
