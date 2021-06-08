package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class ValidationErrorException extends RuntimeException {
    private String field;

    public ValidationErrorException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}