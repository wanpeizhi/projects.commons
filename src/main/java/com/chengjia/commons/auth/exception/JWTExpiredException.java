package com.chengjia.commons.auth.exception;

public class JWTExpiredException extends JWTVerifyException {
    private static final long serialVersionUID = 8658948329032686936L;
	private long expiration;

    public JWTExpiredException(long expiration) {
        this.expiration = expiration;
    }

    public JWTExpiredException(String message, long expiration) {
        super(message);
        this.expiration = expiration;
    }

    public long getExpiration() {
        return expiration;
    };

}
