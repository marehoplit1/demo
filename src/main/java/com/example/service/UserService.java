package com.example.service;

import com.example.model.User;

/**
 * @author mgudelj
 */
public interface UserService {
    User getByUsername(String username);

    void save(User user);
}
