package com.lovehins.auth.client.exception;

/**
 * jwt token 失效异常
 */
public class JwtTokenExpiredException extends Exception {
    public JwtTokenExpiredException(String s) {
        super(s);
    }
}
