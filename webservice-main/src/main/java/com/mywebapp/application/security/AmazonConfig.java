package com.mywebapp.application.security;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Amazon S3.
 */
@Configuration
public class AmazonConfig {

    /**
     * Configures and returns an Amazon S3 client.
     * @return AmazonS3 client instance configured with appropriate credentials and region.
     */
    @Bean
    public AmazonS3 s3() {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion("us-east-1")
                .build();

        return s3;
    }
}
