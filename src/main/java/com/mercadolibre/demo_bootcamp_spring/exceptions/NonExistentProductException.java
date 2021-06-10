package com.mercadolibre.demo_bootcamp_spring.exceptions;

public class NonExistentProductException extends RuntimeException{
    /**
     * Excepción encargada de informar al usuario cuando el ID del producto suministrado no existe.
     */
    public NonExistentProductException() {
        super("The id of the supplied product does not exist.");
    }
}
