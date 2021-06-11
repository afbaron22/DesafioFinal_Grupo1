package com.example.demo_bootcamp_spring.exceptions;

public class InvalidSectionId extends RuntimeException{
    public InvalidSectionId() {
        super("The given id for the section does not exist. Try again.");
    }
}
