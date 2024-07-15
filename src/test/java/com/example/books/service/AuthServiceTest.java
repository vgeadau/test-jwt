package com.example.books.service;

import com.example.books.model.AuthRequest;
import com.example.books.security.JwtUtil;
import com.example.books.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for class {@link AuthService}.
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static final String TOKEN = "some token";

    @Mock
    private MyUserDetailsService userDetailsService = Mockito.mock(MyUserDetailsService.class);

    @Mock
    private JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);

    @Mock
    private AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

    @Mock
    private ValidationService validationService = Mockito.mock(ValidationService.class);

    @InjectMocks
    private AuthService target;

    @Test
    public void authenticate_should_succeed() throws Exception {
        // given
        AuthRequest authRequest = buildAuthRequest();
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(authRequest.username())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(TOKEN);

        // when
        String token = target.authenticate(authRequest);

        // then
        verify(validationService).performAuthenticateValidations(authRequest.username());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(authRequest.username());
        verify(jwtUtil).generateToken(userDetails);
        verifyNoMoreInteractions(validationService, authenticationManager, userDetailsService, jwtUtil);

        assertEquals(TOKEN, token);
    }

    @Test
    public void authenticate_with_errorOnAuthenticateManagerAuthenticateCall_should_fail() {
        // given
        AuthRequest authRequest = buildAuthRequest();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        // when
        Exception exception = assertThrows(Exception.class,
                () -> target.authenticate(authRequest));

        // then
        verify(validationService).performAuthenticateValidations(authRequest.username());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoMoreInteractions(validationService, authenticationManager);
        verifyNoInteractions(userDetailsService, jwtUtil);

        assertEquals(ErrorMessages.AUTHENTICATION_ERROR, exception.getMessage());
    }

    /**
     * builds an object used for testing.
     * @return AuthRequest
     */
    private AuthRequest buildAuthRequest() {
        return new AuthRequest("user", "pass");
    }
}
