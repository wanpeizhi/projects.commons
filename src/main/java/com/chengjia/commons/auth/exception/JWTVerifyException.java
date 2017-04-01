package com.chengjia.commons.auth.exception;

public class JWTVerifyException extends Exception {

	private static final long serialVersionUID = 4583908463244651603L;

	public JWTVerifyException() {
	}

	public JWTVerifyException(String message, Throwable cause) {
		super(message, cause);
	}

	public JWTVerifyException(String message) {
		super(message);
	}

}
