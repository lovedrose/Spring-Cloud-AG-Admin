package com.lovehins.auth.client.exception;

/**
 * jwt 签名异常
 */
public class JwtSignatureException extends Exception {
    public JwtSignatureException(String s) {
        super(s);
    }
}
