package com.lovehins.auth.client.exception;

/**
 * jwt 非法参数异常
 */
public class JwtIllegalArgumentException extends Exception {
    public JwtIllegalArgumentException(String s) {
        super(s);
    }
}
