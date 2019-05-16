package com.mycompany.discount.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycompany.discount.dto.ErrorResponseDTO;
import com.mycompany.discount.exception.UserDoesNotExistException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DiscountServiceControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorResponseDTO onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("Exception handled in onMethodArgumentNotValidException()", e);
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setErrorCode("400.000.001");
		error.setErrorMessage("Request is Invalid");
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.getTraceMessages().add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
		}
		return error;
	}

	@ExceptionHandler(UserDoesNotExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorResponseDTO onUserDoesNotExistException(UserDoesNotExistException e) {
		log.error("Exception handled in onUserDoesNotExistException()", e);
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setErrorCode("400.000.002");
		error.setErrorMessage("User doesn't exists.");
		error.getTraceMessages().add(e.getMessage());
		return error;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ErrorResponseDTO onException(Exception e) {
		log.error("Exception handled in onException()", e);
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setErrorCode("500.000.001");
		error.setErrorMessage("Internal server error, contact support team.");
		error.getTraceMessages().add(e.getMessage());
		return error;
	}

}
