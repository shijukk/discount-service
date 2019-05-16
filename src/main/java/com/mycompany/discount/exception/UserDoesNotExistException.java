package com.mycompany.discount.exception;

public class UserDoesNotExistException extends BaseException {

	/**
	 */
	private static final long serialVersionUID = -1441068187682864204L;

	public UserDoesNotExistException(String errorDescription) {
		super(errorDescription);
	}

}
