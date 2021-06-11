package com.example.demo2.Unit.controller;

import com.example.demo_bootcamp_spring.dtos.JwtRequest;
import com.example.demo_bootcamp_spring.dtos.JwtResponse;
import com.example.demo_bootcamp_spring.dtos.UserDto;
import com.example.demo_bootcamp_spring.models.Account;
import com.example.demo_bootcamp_spring.services.JwtUserDetailsService;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;


    @Test
    public void shouldReturnNewToken() throws Exception {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("user","password", AuthorityUtils.createAuthorityList("BUYER"));
        when(jwtUserDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(new JwtRequest("user","password"))))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(new ObjectMapper().writeValueAsString(new JwtResponse("token"))));
    }

    @Test
    public void shouldRegisterNewUser() throws Exception {
        when(jwtUserDetailsService.save(new UserDto("user","password","REPRESENTATIVE"))).thenReturn(new Account(1,"user","password","REPRESENTATIVE"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new UserDto("user","password","REPRESENTATIVE"))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(new Account(1,"user","password","REPRESENTATIVE"))));;
    }
}