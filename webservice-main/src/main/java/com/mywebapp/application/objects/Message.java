package com.mywebapp.application.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a message entity containing email, token, and message type.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Message {
    private String email;
    private String token;
    private String messageType;
}
