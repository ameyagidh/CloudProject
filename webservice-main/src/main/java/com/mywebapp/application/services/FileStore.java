package com.mywebapp.application.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mywebapp.application.security.BucketCreated;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing file storage operations.
 */
@AllArgsConstructor
@Service
public class FileStore {

    @Value("${cloud.aws.bucketname}")
    private String bucketName;

    @Autowired
    private BucketCreated bucketCreated;

    private final AmazonS3 amazonS3;

    /**
     * Uploads a file to the specified path.
     * @param path The path to upload the file.
     * @param fileName The name of the file.
     * @param optionalMetaData Optional metadata for the file.
     * @param inputStream The input stream of the file.
     * @return The URL of the uploaded file.
     */
    public String upload(String path,
                         String fileName,
                         Optional<Map<String, String>> optionalMetaData,
                         InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
            return amazonS3.getResourceUrl(bucketCreated.getBucketName(), fileName);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    /**
     * Deletes a file from the specified path.
     * @param path The path of the file to delete.
     * @param fileName The name of the file to delete.
     */
    public void delete(String path, String fileName) {
        try {
            amazonS3.deleteObject(path, fileName);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete the file", e);
        }
    }
}
