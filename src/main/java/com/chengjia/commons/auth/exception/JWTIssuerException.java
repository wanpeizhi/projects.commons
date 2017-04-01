package com.chengjia.commons.auth.exception;

public class JWTIssuerException extends JWTVerifyException {
	private static final long serialVersionUID = 6650238017269997995L;
	private final String issuer;

	public JWTIssuerException(String issuer) {
		this.issuer = issuer;
	}

	public JWTIssuerException(String message, String issuer) {
		super(message);
		this.issuer = issuer;
	}

	public String getIssuer() {
		return issuer;
	}

}
