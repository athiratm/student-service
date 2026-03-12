package com.skiply.student_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Exception handler class
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * To handle ResourceNotFoundException for student not exists scenario
	 * 
	 * @param ex The exception thrown when a resource is not found.
	 * @param request The HTTP request that triggered the exception.
	 * @return A response entity with a not found error.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * To handle StudentAlreadyExistsException for student doesn't exist scenario
	 * 
	 * @param ex The exception thrown when a student already exists.
	 * @param request The HTTP request that triggered the exception.
	 * @return A response entity with a conflict error.
	 */
	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleStudentAlreadyExists(StudentAlreadyExistsException ex, HttpServletRequest request) {
		return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	/**
	 * To handle all other exceptions
	 * 
	 * @param ex The exception thrown.
	 * @param request The HTTP request that triggered the exception.
	 * @return A response entity with an internal server error.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex,
			HttpServletRequest request) {
		return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Helper method to define error object and error response entity
	 *
	 * @param message Exception message
	 * @param status Http status code for exception
	 * @return A resonse entity with error
	 */
	private ResponseEntity<ApiErrorResponse> createErrorResponse(String message, HttpStatus status) {
		ApiErrorResponse error = new ApiErrorResponse(LocalDateTime.now(), status.value(), message);
		return new ResponseEntity<>(error, status);
	}
}
