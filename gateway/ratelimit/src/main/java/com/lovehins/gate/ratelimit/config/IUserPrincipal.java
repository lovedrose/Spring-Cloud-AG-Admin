package com.lovehins.gate.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lovedrose on 2017/9/23.
 */
public interface IUserPrincipal {
    String getName(HttpServletRequest request);
}
