package com.example.books.controller;

import com.example.books.model.AuthRequest;
import com.example.books.model.User;
import com.example.books.service.AuthService;
import com.example.books.service.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AuthController}.
 * </br>
 * Note: "verifies" gives us certainty that if we somehow change the code in order to test faster a functionality,
 * we don't forget to re-add the code back.
 */
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private static final String TOKEN = "some token is here...";

    @Mock
    private AuthService authService = Mockito.mock(AuthService.class);

    @Mock
    private MyUserDetailsService userDetailsService = Mockito.mock(MyUserDetailsService.class);

    @InjectMocks
    private AuthController target;

    @Test
    public void authenticate_should_succeed() throws Exception {
        // given
        AuthRequest authRequest = buildAuthRequest();

        when(authService.authenticate(authRequest)).thenReturn(TOKEN);

        // when
        String result = target.authenticate(authRequest);

        // then
        verify(authService).authenticate(authRequest);
        verifyNoMoreInteractions(authService);
        verifyNoInteractions(userDetailsService);

        assertEquals(TOKEN, result);
    }

    @Test
    public void register_should_succeed() {
        // given
        User user = buildUser();

        when(userDetailsService.save(user)).thenReturn(user);

        // when
        User result = target.register(user);

        // then
        verify(userDetailsService).save(user);
        verifyNoMoreInteractions(userDetailsService);
        verifyNoInteractions(authService);

        assertEquals(user, result);
    }

    /**
     * builds an object used for testing.
     * @return AuthRequest
     */
    private AuthRequest buildAuthRequest() {
        return new AuthRequest("user", "pass");
    }

    /**
     * builds an object used for testing.
     * @return User
     */
    private User buildUser() {
        final User user = new User();
        user.setPassword("pass");
        user.setPseudonym("pseudonym");
        user.setUsername("user");
        return user;
    }

}
