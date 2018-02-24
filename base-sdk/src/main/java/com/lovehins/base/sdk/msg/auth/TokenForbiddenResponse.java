package com.lovehins.base.sdk.msg.auth;

import com.lovehins.base.sdk.constant.RestCodeConstants;
import com.lovehins.base.sdk.msg.BaseResponse;

/**
 * Created by lovedrose on 2017/8/25.
 */
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
