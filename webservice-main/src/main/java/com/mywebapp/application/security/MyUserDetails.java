package com.mywebapp.application.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * Custom UserDetails implementation representing authenticated users.
 */
public class MyUserDetails implements UserDetails {

    private String username;

    /**
     * Constructs a MyUserDetails instance with the specified username.
     * @param username The username of the user.
     */
    MyUserDetails(String username){
        this.username = username;
    }

    /**
     * Retrieves the authorities granted to the user.
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Retrieves the password of the user.
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return "pass";
    }

    /**
     * Retrieves the username of the user.
     * @return The username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the user's account is non-expired.
     * @return true if the account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the user's account is non-locked.
     * @return true if the account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the user's credentials are non-expired.
     * @return true if the credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the user is enabled.
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
