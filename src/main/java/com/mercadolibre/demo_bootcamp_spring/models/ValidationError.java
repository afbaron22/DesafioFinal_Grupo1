package com.mercadolibre.demo_bootcamp_spring.models;

public class ValidationError {
    private String field;
    private String message;
    private int codigo;

    public ValidationError(String field, String message, int codigo) {
        this.setField(field);
        this.setMessage(message);
        this.setCodigo(codigo);
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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int code) {
        this.codigo = code;
    }
}