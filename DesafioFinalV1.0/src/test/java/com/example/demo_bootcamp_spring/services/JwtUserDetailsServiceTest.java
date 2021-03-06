package com.example.demo_bootcamp_spring.services;

import com.example.demo_bootcamp_spring.dtos.UserDto;
import com.example.demo_bootcamp_spring.models.Account;
import com.example.demo_bootcamp_spring.models.Warehouse;
import com.example.demo_bootcamp_spring.repository.UserRepository;
import com.example.demo_bootcamp_spring.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    private String username;
    private Account user;
    private UserDto userDto;
    private Warehouse warehouse;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder bcryptEncoder;
    @Mock
    private WarehouseRepository warehouseRepository;
    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    @BeforeEach
    void setup(){
        warehouse = new Warehouse(1,"address", "location","province");
        user = new Account(1,"username","password","role",warehouse);
        userDto = new UserDto("username","password","role",1);
        username = "username";
    }

    @Test
    public void shouldLoadUser(){
        //
        //WHEN
        when(userRepository.findByUsername(username)).thenReturn(user);
        //THEN
        UserDetails userDetails = userDetailsService.loadUserByUsername("username");
        assertEquals(username,userDetails.getUsername());

    }

    @Test
    public void shouldNotLoadUser(){
        //WHEN
        when(userRepository.findByUsername(username)).thenReturn(null);
        //THEN

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }

    @Test
    public void shouldSaveUser(){

        //WHEN
        when(bcryptEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(userRepository.save(any(Account.class))).thenReturn(user);
        when(warehouseRepository.findById(any(Integer.class))).thenReturn(Optional.of(warehouse));
        //THEN
        assertEquals(user,userDetailsService.save(userDto));
    }
}