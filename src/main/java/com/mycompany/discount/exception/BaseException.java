package com.mycompany.discount.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	/**
	 */
	private static final long serialVersionUID = 1294339396723855571L;

	public BaseException(String errorDescription) {
		super(errorDescription);
	}
}
