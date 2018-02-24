package com.lovehins.base.sdk.exception.auth;


import com.lovehins.base.sdk.constant.CommonConstants;
import com.lovehins.base.sdk.exception.BaseException;

/**
 * Created by lovedrose
 */
public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
