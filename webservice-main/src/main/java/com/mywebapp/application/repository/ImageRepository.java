package com.mywebapp.application.repository;

import com.mywebapp.application.objects.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Image entities.
 * It extends CrudRepository to provide basic CRUD operations for Image entities.
 */
@Repository
public interface ImageRepository extends CrudRepository<Image, String> {

    /**
     * Retrieves an image by user ID.
     *
     * @param userId The ID of the user associated with the image.
     * @return The image associated with the specified user ID.
     */
    Image findByuserId(String userId);

    /**
     * Deletes an image by user ID.
     *
     * @param userId The ID of the user associated with the image to be deleted.
     */
    void deleteById(String userId);
}
