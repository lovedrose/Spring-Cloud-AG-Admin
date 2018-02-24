package com.lovehins.gate.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lovedrose on 2017/9/23.
 */
public class DefaultUserPrincipal implements IUserPrincipal {
    @Override
    public String getName(HttpServletRequest request) {
        if(request.getUserPrincipal()==null) {
            return null;
        }
        return request.getUserPrincipal().getName();
    }
}
