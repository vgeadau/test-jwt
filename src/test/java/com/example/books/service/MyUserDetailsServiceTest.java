package com.example.books.service;

import com.example.books.model.User;
import com.example.books.repository.UserRepository;
import com.example.books.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

    private static final String USER_NAME = "username";
    private static final String INVALID_USER_NAME = "invalid username";
    private static final Long ID = 1L;
    private static final String ENCODED_PASSWORD = "encoded password";

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    @Mock
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    @InjectMocks
    private MyUserDetailsService target;

    @Test
    public void loadUserByUsername_should_succeed() {
        // given
        User user = buildUser();

        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        // when
        UserDetails userDetails = target.loadUserByUsername(USER_NAME);

        // then
        verify(userRepository).findByUsername(USER_NAME);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);

        assertEquals(userDetails.getUsername(), user.getUsername());
        assertEquals(userDetails.getPassword(), user.getPassword());
    }

    @Test
    public void loadUserByUsername_withUserNotFound_should_fail() {
        // given
        when(userRepository.findByUsername(INVALID_USER_NAME)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(UsernameNotFoundException.class,
                () -> target.loadUserByUsername(INVALID_USER_NAME));

        // then
        verify(userRepository).findByUsername(INVALID_USER_NAME);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);

        assertEquals(ErrorMessages.NOT_FOUND + INVALID_USER_NAME, exception.getMessage());
    }

    @Test
    public void save_should_succeed() {
        // given
        User user = buildUser();
        String password = "pass";
        user.setPassword(password);

        when(passwordEncoder.encode(user.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(user)).thenReturn(user);

        // when
        User result = target.save(user);

        // then
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(user);

        assertEquals(user, result);
    }

    /**
     * builds an object used for testing.
     * @return User
     */
    private User buildUser() {
        final User user = new User();
        user.setUsername("username");

        user.setId(ID);
        return user;
    }

}
