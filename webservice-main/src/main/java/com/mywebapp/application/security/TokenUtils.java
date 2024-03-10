package com.mywebapp.application.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for token generation and extraction.
 */
@Component
public class TokenUtils {

    /**
     * Generates a token for the given user details.
     * @param userDetails The user details.
     * @return The generated token.
     */
    public String generateToken(UserDetails userDetails) {
        return createToken("salt", userDetails.getPassword());
    }

    private String createToken(String salt, String password) {
        Base64 base64 = new Base64();
        String encoding = base64.encodeAsString(new String("test1:" + password).getBytes());
        return encoding;
    }

    /**
     * Extracts the token from the given token string.
     * @param token The token string.
     * @return The extracted token.
     */
    public String extractToken(String token) {
        String decodedToken = decodeToken(getToken(token));
        return decodedToken.split(":")[1];
    }

    /**
     * Extracts the username from the given token string.
     * @param token The token string.
     * @return The extracted username.
     */
    public String extractUserName(String token) {
        token = getToken(token);
        String decodedToken = decodeToken(token);
        return decodedToken.split(":")[0];
    }

    /**
     * Extracts the password from the given token string.
     * @param token The token string.
     * @return The extracted password.
     */
    public String extractPassword(String token) {
        token = getToken(token);
        String decodedToken = decodeToken(token);
        return decodedToken.split(":")[1];
    }

    private String getToken(String token) {
        return token.split(" ")[1];
    }

    private String decodeToken(String jwt) {
        Base64 base64 = new Base64();
        byte[] decoding = base64.decode(jwt);
        return new String(decoding);
    }
}
