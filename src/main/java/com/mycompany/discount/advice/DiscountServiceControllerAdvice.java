package com.mycompany.discount.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycompany.discount.dto.ErrorResponseDTO;

@ControllerAdvice
public class DiscountServiceControllerAdvice {

	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorResponseDTO onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorResponseDTO error = new ErrorResponseDTO();
		error.setErrorCode("400.000.001");
		error.setErrorMessage("Request is Invalid");
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.getTraceMessages().add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
		}
		return error;
	}
	
	
}
