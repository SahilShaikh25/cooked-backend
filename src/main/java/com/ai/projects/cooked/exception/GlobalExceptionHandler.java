package com.ai.projects.cooked.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) 
	{

		return ResponseEntity
				.status(503)
				.body("🔥 The roasting kitchen is overloaded right now. Please try again in a few minutes.");
	}
}
