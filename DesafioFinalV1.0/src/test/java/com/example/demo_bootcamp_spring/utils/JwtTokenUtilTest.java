package com.example.demo_bootcamp_spring.utils;

import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setup(){
        jwtTokenUtil = new JwtTokenUtil("secret");
    }

    @Test
    void shouldReturnUserNameFromToken() {
        //Given
        UserDetails userDetails = new User("Juan", "password", new ArrayList<>());
        final String token = jwtTokenUtil.generateToken(userDetails);

        //Then
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        assertEquals("Juan", username);
    }

    @Test
    void shouldValidateToken() {
        //Given
        UserDetails userDetails = new User("john", "password", new ArrayList<>());
        final String token = jwtTokenUtil.generateToken(userDetails);

        final boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

        //Then
        assertTrue(isValid);
    }

}