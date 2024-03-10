package com.mywebapp.application.services;

import com.mywebapp.application.objects.Image;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for managing image-related operations.
 */
public interface ImageService {
    
    /**
     * Saves an image to the specified path.
     * 
     * @param path The path where the image will be saved.
     * @param fileName The name of the image file.
     * @param file The image file to be saved.
     * @return The saved Image object.
     */
    Image saveImage(String path, String fileName, MultipartFile file);
}
