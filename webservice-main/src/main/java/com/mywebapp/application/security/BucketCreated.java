package com.mywebapp.application.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class representing information about the created bucket.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Component
public class BucketCreated {

    /**
     * The name of the created bucket.
     */
    @Value("${cloud.aws.bucketname}")
    private String bucketName;
}
