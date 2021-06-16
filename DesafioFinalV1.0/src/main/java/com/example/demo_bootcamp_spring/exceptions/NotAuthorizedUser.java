package com.example.demo_bootcamp_spring.exceptions;

public class NotAuthorizedUser extends RuntimeException{
    public NotAuthorizedUser() {
        super("Not authorized user");
    }
}
