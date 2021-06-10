package com.mercadolibre.demo_bootcamp_spring.config;

import com.mercadolibre.demo_bootcamp_spring.exceptions.*;
import com.mercadolibre.demo_bootcamp_spring.models.ErrorMessage;
import com.mercadolibre.demo_bootcamp_spring.models.ValidationError;
import com.newrelic.api.agent.NewRelic;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
		ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<ApiError> handleApiException(ApiException e) {
		Integer statusCode = e.getStatusCode();
		boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
		NewRelic.noticeError(e, expected);
		if (expected) {
			LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
		} else {
			LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
		}

		ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
		LOGGER.error("Internal error", e);
		NewRelic.noticeError(e);

		ApiError apiError = new ApiError("internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(apiError.getStatus())
				.body(apiError);
	}

	@ExceptionHandler({NonExistentProductException.class})
	public ResponseEntity<?> handleResponseExceptions(RuntimeException runtimeException){
		ErrorMessage errorMessage = new ErrorMessage(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationErrorException.class)
	public ResponseEntity<?> ValidationErrorException(ValidationErrorException validationErrorException){
		ValidationError validationError = new ValidationError(validationErrorException.getField(), validationErrorException.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ValidationError>(validationError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductsOutOfStockException.class)
	public ResponseEntity<?> ProductOutOfStockException(ProductsOutOfStockException productsOutOfStockException){
		ErrorMessage errorMessage = new ErrorMessage(productsOutOfStockException.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}