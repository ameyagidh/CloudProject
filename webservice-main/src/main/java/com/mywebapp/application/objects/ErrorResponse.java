package com.mywebapp.application.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error response object.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
}
