package com.example.demo_bootcamp_spring.services;

import com.example.demo_bootcamp_spring.dtos.JwtRequest;
import com.example.demo_bootcamp_spring.dtos.JwtResponse;
import com.example.demo_bootcamp_spring.dtos.UserDto;
import com.example.demo_bootcamp_spring.exceptions.InvalidWarehouseException;
import com.example.demo_bootcamp_spring.models.Account;
import com.example.demo_bootcamp_spring.repository.UserRepository;
import com.example.demo_bootcamp_spring.repository.WarehouseRepository;
import com.example.demo_bootcamp_spring.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
    }

    public Account save(UserDto user) {
        Account newUser = new Account();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setWarehouse(warehouseRepository.findById(user.getIdWarehouse()).get());
        return userDao.save(newUser);
    }

    public JwtResponse authenticate(JwtRequest authenticationRequest){
        Account user = userDao.findByUsername(authenticationRequest.getUsername());
        UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
        if(user.getWarehouse().getIdWarehouse() != authenticationRequest.getWarehouseId()){
            throw new InvalidWarehouseException("User does not exist on warehouse: " + authenticationRequest.getWarehouseId());
        }
        return new JwtResponse(jwtTokenUtil.generateToken(userDetails));
    }
}
