package com.mywebapp.application.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an authentication response containing a token.
 */
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
}
