package com.lovehins.base.sdk.msg.auth;

import com.lovehins.base.sdk.constant.RestCodeConstants;
import com.lovehins.base.sdk.msg.BaseResponse;

/**
 * Created by lovedrose on 2017/8/23.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
