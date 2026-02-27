package com.medicore.auth.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medicore.auth.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserExists(UserAlreadyExistsException ex){
		ApiResponse<Object> response =
                new ApiResponse<>("ERROR", ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
	        MethodArgumentNotValidException ex) {
		logger.warn("Validation failed: {}", ex);

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	    );

	    ApiResponse<Map<String, String>> response =
	            new ApiResponse<>("ERROR", "Validation failed", errors);

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	 

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {

	    logger.error("Unexpected error occurred: ", ex);

	    ApiResponse<Object> response =
	            new ApiResponse<>("ERROR", "Internal server error", null);

	    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);
}
