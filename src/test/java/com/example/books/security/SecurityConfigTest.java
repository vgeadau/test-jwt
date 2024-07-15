package com.example.books.security;

import com.example.books.service.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SecurityConfig}.
 */
@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

    @Mock
    private MyUserDetailsService myUserDetailsService = Mockito.mock(MyUserDetailsService.class);
    @Mock
    private JwtRequestFilter jwtRequestFilter = Mockito.mock(JwtRequestFilter.class);

    @InjectMocks
    private SecurityConfig target;

    @Test
    public void passwordEncoder_should_succeed() {

        // when
        PasswordEncoder result = target.passwordEncoder();

        // then
        verifyNoInteractions(myUserDetailsService, jwtRequestFilter);

        assertNotNull(result);
    }

    @Test
    @SuppressWarnings({"unchecked","rawtypes"})
    public void configure_withAuthenticationManagerBuilderParam_should_succeed() throws Exception {
        // given
        AuthenticationManagerBuilder builder = Mockito.mock(AuthenticationManagerBuilder.class);
        DaoAuthenticationConfigurer configurer = Mockito.mock(DaoAuthenticationConfigurer.class);
        String error = "NO ERROR";

        when(builder.userDetailsService(myUserDetailsService)).thenReturn(configurer);

        // when
        try {
            target.configure(builder);
        } catch (Exception e) {
            error = "ERROR";
        }

        // then
        verify(builder).userDetailsService(myUserDetailsService);
        verifyNoInteractions(myUserDetailsService, jwtRequestFilter);

        assertEquals("NO ERROR", error);
    }
}
