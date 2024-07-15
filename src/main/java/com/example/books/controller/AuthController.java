package com.example.books.controller;

import com.example.books.model.AuthRequest;
import com.example.books.model.User;
import com.example.books.service.AuthService;
import com.example.books.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller.
 * </br>
 * First we need to /register a user in order to be able to call /authenticate API.
 * Once a user is registered calling an authenticate will offer a LOGIN TOKEN
 * That LOGIN_TOKEN must be set as a header on CRUD BOOKS API:
 * Key: Authorization
 * Value: Bearer LOGIN_TOKEN
 * </br>
 * Note. This can be further improved by having API documentation automatically generated
 * using the OPENAPI framework.
 */
@RestController
public class AuthController {

    private final AuthService authService;

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public AuthController(AuthService authService, MyUserDetailsService userDetailsService) {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * API responsible with the authentication of a User.
     * @param authRequest AuthRequest class containing username and password
     * @return a LOGIN_TOKEN string which could be used as a header for CRUD ops on books.
     * @throws Exception in case of Internal error
     */
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        return authService.authenticate(authRequest);
    }

    /**
     * API responsible with registering of a User.
     * @param user User object
     * @return the registered user
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userDetailsService.save(user);
    }
}