package com.example.demo_bootcamp_spring.controller;

import com.example.demo_bootcamp_spring.dtos.ValidationErrorDTO;
import com.example.demo_bootcamp_spring.exceptions.*;
import com.example.demo_bootcamp_spring.models.ErrorMessage;
import com.example.demo_bootcamp_spring.models.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionControllerAdvice {
    @ExceptionHandler({NonExistentProductException.class})
    public ResponseEntity<?> handleResponseExceptions(RuntimeException runtimeException) {
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> ProductNotFoundException(ProductNotFoundException productNotFoundException){
        ErrorMessage errorMessage = new ErrorMessage(productNotFoundException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> OrderNotFoundException(OrderNotFoundException orderNotFoundException){
        ErrorMessage errorMessage = new ErrorMessage(orderNotFoundException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotAuthorizedUser.class})
    public ResponseEntity<?> handleResponseExceptionsNotAuthorizedUser(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExistingInboundOrderId.class})
    public ResponseEntity<?> handleResponseExceptionsExistingInboundOrderId(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidSectionId.class})
    public ResponseEntity<?> handleResponseInvalidSectionId(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoRelatedWarehousesToProduct.class})
    public ResponseEntity<?> handleNoRelatedWarehousesToProduct(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NotExistingBatch.class})
    public ResponseEntity<?> handleNotExistingBatch(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundInboundOrderId.class})
    public ResponseEntity<?> handleNotFoundInboundOrderId(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProductsOutOfStockException.class})
    public ResponseEntity<?> handleProductsOutOfStockException(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidWarehouseException.class})
    public ResponseEntity<?> handleInvalidWarehouseException(RuntimeException runtimeException){
        ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException argumentNotValidException) {
        List<ValidationErrorDTO> valErrorList = new ArrayList<>();
        for (FieldError error : argumentNotValidException.getFieldErrors()) {
            ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
            validationErrorDTO.setField(error.getObjectName() + "." + error.getField());
            validationErrorDTO.setMessage(error.getDefaultMessage());
            valErrorList.add(validationErrorDTO);
        }
        return new ResponseEntity<>(valErrorList, HttpStatus.BAD_REQUEST);
    }


}
