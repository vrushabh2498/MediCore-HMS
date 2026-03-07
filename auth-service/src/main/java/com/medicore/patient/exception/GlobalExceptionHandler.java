package com.medicore.patient.exception;


import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medicore.patient.dto.ApiResponse;
import org.slf4j.Logger;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(PatientAlreadyExitsException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserExists(PatientAlreadyExitsException ex){
		
		ApiResponse<Object> response =
                new ApiResponse<>("ERROR", ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(PatientNotFound.class)
	public ResponseEntity<ApiResponse<Object>> patientNotFound(PatientNotFound ex){
		
		LoggerFactory.getLogger(GlobalExceptionHandler.class);
		logger.error("Unexpected error occurred: ", ex);
		 ApiResponse<Object> response = new ApiResponse<>(
	                "ERROR",
	                ex.getMessage(),
	                null
	        );
	
	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}
	@ExceptionHandler(PatientNotFound.class)
	public ResponseEntity<ApiResponse<Object>> patientNotFoundException(PatientNotFound ex){
		
		LoggerFactory.getLogger(GlobalExceptionHandler.class);
		 logger.warn("Patient not found: {}", ex.getMessage());

		 ApiResponse<Object> response = new ApiResponse<>(
	                "ERROR",
	                ex.getMessage(),
	                null
	        );
	
	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}
}