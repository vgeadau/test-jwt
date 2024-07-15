package com.example.books.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageConverter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link WebConfig}.
 */
@ExtendWith(MockitoExtension.class)
public class WebConfigTest {
    private final WebConfig target = new WebConfig();

    @Test
    public void createXmlHttpMessageConverter_should_succeed() {
        HttpMessageConverter<Object> result = target.createXmlHttpMessageConverter();

        assertNotNull(result);
    }

    @Test
    public void createJsonHttpMessageConverter_should_succeed() {
        HttpMessageConverter<Object> result = target.createJsonHttpMessageConverter();

        assertNotNull(result);
    }
}
