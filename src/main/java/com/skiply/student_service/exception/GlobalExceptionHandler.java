package com.skiply.student_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

		ApiErrorResponse error = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleStudentAlreadyExists(StudentAlreadyExistsException ex, HttpServletRequest request) {
		ApiErrorResponse error = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGlobalException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ApiErrorResponse error = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
