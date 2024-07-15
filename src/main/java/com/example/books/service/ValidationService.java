package com.example.books.service;

import com.example.books.util.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom validations service.
 */
@Service
public class ValidationService {

    public static final List<String> BANNED_LIST = List.of("_Darth Vader_");

    /**
     * One required validations is that we do not allow _Darth Vader_ to authenticate.
     * @param username String
     */
    public void performAuthenticateValidations(String username) {
        if (BANNED_LIST.contains(username)) {
            throw new RuntimeException(ErrorMessages.BANNED_USER);
        }
    }
}
