package com.example.demo_bootcamp_spring.repository;


import com.example.demo_bootcamp_spring.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
