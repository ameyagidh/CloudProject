package com.mywebapp.application.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Represents a DTO (Data Transfer Object) for a user entity.
 * This class is used to transfer user-related data between different layers of the application.
 */
@Getter
@Setter
@Builder
@ToString
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private Date account_created;
    private Date account_updated;
}
