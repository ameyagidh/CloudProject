package com.mywebapp.application.services;

import com.mywebapp.application.objects.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.http.entity.ContentType.*;

/**
 * Implementation of the ImageService interface for managing image-related operations.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final FileStore fileStore;

    public ImageServiceImpl(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    @Override
    public Image saveImage(String path, String fileName, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        // Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        // Get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        try {
            String url = fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        return null;
    }

    /**
     * Deletes an image from the specified path.
     *
     * @param path     The path where the image is stored.
     * @param fileName The name of the image file to be deleted.
     * @return The deleted Image object.
     */
    public Image deleteImage(String path, String fileName) {
        try {
            fileStore.delete(path, fileName);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to delete file", e);
        }
        return null;
    }
}
