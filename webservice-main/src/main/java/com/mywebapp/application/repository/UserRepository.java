package com.mywebapp.application.repository;

import com.mywebapp.application.objects.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities.
 * Extends CrudRepository to provide basic CRUD operations for User entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find a user by username.
     * @param username The username to search for.
     * @return The User object corresponding to the username, or null if not found.
     */
    User findByUsername(String username);

    /**
     * Find a user by ID.
     * @param id The ID of the user to search for.
     * @return The User object corresponding to the ID, or null if not found.
     */
    User findById(long id);

}
