package com.example.security;

import lombok.Data;

@Data
public class SecurityUser {

    private String name;

    private String lastname;

    private String username;

    private String password;

    private UserRole role;
}
