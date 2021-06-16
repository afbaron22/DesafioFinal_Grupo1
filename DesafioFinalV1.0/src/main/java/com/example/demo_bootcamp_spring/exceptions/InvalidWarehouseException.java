package com.example.demo_bootcamp_spring.exceptions;

public class InvalidWarehouseException extends RuntimeException{
    public InvalidWarehouseException(String message){
        super(message);
    }
}
