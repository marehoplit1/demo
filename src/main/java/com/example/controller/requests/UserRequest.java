package com.example.controller.requests;

import com.example.security.UserRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author mgudelj
 */
@Data
public class UserRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private UserRole role;
}
