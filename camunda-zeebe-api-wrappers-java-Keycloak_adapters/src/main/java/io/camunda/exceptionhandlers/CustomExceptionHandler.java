package io.camunda.exceptionhandlers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.camunda.exceptions.response.ValidationErrorResponse;
import io.camunda.exceptions.response.Violation;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			error.getViolations().add(new Violation(violation.getMessage()));
		}
		return error;
	}
	
	@ExceptionHandler(value =  {Exception.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Violation onException(Exception e) {		
		return new Violation(e.getMessage());
	}  

}