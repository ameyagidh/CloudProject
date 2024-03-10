package com.mywebapp.application.security;

import com.mywebapp.application.repository.UserRepository;
import com.mywebapp.application.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service class for loading user-specific data during authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Loads a user by their username from the database.
     * @param username The username to load the user by.
     * @return UserDetails representing the loaded user.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Create UserDetails object for the found user
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    /**
     * Validates user credentials against the database.
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateUser(String username, String password){
        User user = userRepository.findByUsername(username);
        // Check if user exists and the password matches the encoded password in the database
        return user != null && username.equals(user.getUsername()) && passwordEncoder.matches(password, user.getPassword());
    }
}
