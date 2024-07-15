package com.example.books.service;


import com.example.books.util.ErrorMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link ValidationService}.
 */
public class ValidationServiceTest {

    private final ValidationService target = new ValidationService();

    @Test
    public void performAuthenticateValidations_withBannedUser_should_fail() {
        // given
        String username = ValidationService.BANNED_LIST.get(0);

        // when
        Exception exception = assertThrows(RuntimeException.class,
                () -> target.performAuthenticateValidations(username));

        // then
        assertEquals(ErrorMessages.BANNED_USER, exception.getMessage());
    }

    @Test
    public void performAuthenticateValidations_should_succeed() {
        // given
        String username = "some user different from Darth Vader";
        String noError = "NO ERROR";

        try {
            target.performAuthenticateValidations(username);
        } catch (Exception e) {
            noError = "ERROR FOUND";
        }

        assertEquals("NO ERROR", noError);
    }
}
