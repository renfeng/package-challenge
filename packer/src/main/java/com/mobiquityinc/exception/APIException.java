package com.mobiquityinc.exception;

public class APIException extends RuntimeException {

	public APIException(Throwable cause) {
		super(cause);
	}

	public APIException(String message) {
		super(message);
	}
}
