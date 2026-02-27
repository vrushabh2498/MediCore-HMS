package com.medicore.auth.exception;

public class UserAlreadyExistsException extends RuntimeException{
   
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
