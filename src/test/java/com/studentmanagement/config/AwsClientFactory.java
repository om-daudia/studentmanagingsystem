package com.studentmanagement.config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

public class AwsClientFactory {
    public static S3Client mockS3Client(String mockEndpoint){
        return S3Client.builder()
                .endpointOverride(URI.create(mockEndpoint)) // http://localhost:8089
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("dummy-access", "dummy-secret")
                        )
                )
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // Required for mocks
                        .build())
                .build();

    }
}
