package com.example.demo_bootcamp_spring.config;
import com.example.demo_bootcamp_spring.services.JwtUserDetailsService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
class JwtRequestFilterTest {

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    private final JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();

    @Test
    public void testFilterOk() throws ServletException, IOException {

        //GIVEN
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);
        final UserDetails userDetails = mock(UserDetails.class);

        //WHEN

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("username");
        when(jwtUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken("token",userDetails)).thenReturn(true);

        //THEN
        assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(request, response, chain));
        verify(chain, times(1)).doFilter(request,response);
    }

    @Test
    public void shouldFilterWithOutToken() throws ServletException, IOException {

        //GIVEN
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        //WHEN
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtTokenUtil.getUsernameFromToken("token")).thenThrow(IllegalArgumentException.class);

        //THEN
        assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(request, response, chain));
        verify(chain, times(1)).doFilter(request,response);

    }

    @Test
    public void shouldFilterWithTokenExpired() throws ServletException, IOException {//GIVEN

        //GIVEN
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain chain = mock(FilterChain.class);

        //WHEN
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtTokenUtil.getUsernameFromToken("token")).thenThrow(ExpiredJwtException.class);

        //THEN
        assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(request, response, chain));
        verify(chain, times(1)).doFilter(request,response);

    }

}