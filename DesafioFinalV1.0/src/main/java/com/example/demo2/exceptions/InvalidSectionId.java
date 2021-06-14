package com.example.demo2.exceptions;

public class InvalidSectionId extends RuntimeException{
    public InvalidSectionId() {
        super("The given id for the section does not exist. Try again.");
    }
}
