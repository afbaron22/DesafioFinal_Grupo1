package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.exceptions.ExistingInboundOrderId;
import com.example.demo_bootcamp_spring.exceptions.NonExistentProductException;
import com.example.demo_bootcamp_spring.exceptions.ValidationErrorException;
import com.example.demo_bootcamp_spring.models.ErrorMessage;
import com.example.demo_bootcamp_spring.models.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ApiExceptionControllerAdvice {
    @ExceptionHandler({NonExistentProductException.class})
    public ResponseEntity<?> handleResponseExceptions(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ExistingInboundOrderId.class})
    public ResponseEntity<?> handleResponseExceptionsExistingInboundOrderId(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<?> ValidationErrorException(ValidationErrorException validationErrorException){
        ValidationError validationError = new ValidationError(validationErrorException.getField(), validationErrorException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ValidationError>(validationError, HttpStatus.BAD_REQUEST);
    }
}