package com.example.controller;

import com.example.controller.requests.UserRequest;
import com.example.model.User;
import com.example.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author mgudelj
 */

@Log4j2
@Validated
@RestController
@RequestMapping("/api/rest/v1")
public class UserController {

    UserService userService;
    PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/users/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCity(@Valid @RequestBody UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getName());
        user.setLastName(userRequest.getLastname());
        user.setUsername(userRequest.getUsername());
        user.setUserRole(userRequest.getRole());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userService.save(user);
    }
}