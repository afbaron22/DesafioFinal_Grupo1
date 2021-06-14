package com.example.demo2.exceptions;

public class NotExistingBatch extends RuntimeException{
    public NotExistingBatch() {
        super("Not existent batch");
    }
}
