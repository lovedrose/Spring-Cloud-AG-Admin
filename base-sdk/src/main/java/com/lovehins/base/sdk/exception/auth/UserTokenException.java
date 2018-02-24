package com.lovehins.base.sdk.exception.auth;


import com.lovehins.base.sdk.constant.CommonConstants;
import com.lovehins.base.sdk.exception.BaseException;

/**
 * Created by lovedrose
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
