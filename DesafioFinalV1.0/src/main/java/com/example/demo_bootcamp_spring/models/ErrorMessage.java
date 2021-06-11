package com.example.demo_bootcamp_spring.models;

public class ErrorMessage {
    private String message;
    private int code;

    public ErrorMessage(String message, int code) {
        this.setMessage(message);
        this.setCode(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
