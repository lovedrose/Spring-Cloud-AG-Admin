package com.lovehins.base.sdk.exception.auth;


import com.lovehins.base.sdk.constant.CommonConstants;
import com.lovehins.base.sdk.exception.BaseException;

/**
 * Created by lovedrose
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
