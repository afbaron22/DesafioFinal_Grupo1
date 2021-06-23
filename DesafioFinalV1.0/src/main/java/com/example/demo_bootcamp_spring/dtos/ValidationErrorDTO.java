package com.example.demo_bootcamp_spring.dtos;

public class ValidationErrorDTO {
    private String field;
    private String message;

    public ValidationErrorDTO() {

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
