package com.mycompany.discount.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDTO {

	/**
	 * 
	 */
	private String errorCode;

	/**
	 * 
	 */
	private String errorMessage;

	/**
	 * 
	 */
	private List<String> traceMessages = new ArrayList<>();

}
