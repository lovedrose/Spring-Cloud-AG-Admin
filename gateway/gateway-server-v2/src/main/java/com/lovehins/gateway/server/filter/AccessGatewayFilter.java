package com.lovehins.gateway.server.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lovehins.auth.client.config.ServiceAuthConfig;
import com.lovehins.auth.client.config.UserAuthConfig;
import com.lovehins.auth.client.jwt.ServiceAuthUtil;
import com.lovehins.auth.client.jwt.UserAuthUtil;
import com.lovehins.auth.common.util.jwt.IJWTInfo;
import com.lovehins.base.sdk.msg.BaseResponse;
import com.lovehins.gateway.server.feign.ILogService;
import com.lovehins.gateway.server.feign.IUserService;
import com.lovehins.gateway.server.utils.DBLog;
import com.lovehins.base.sdk.context.BaseContextHandler;
import com.lovehins.base.sdk.msg.auth.TokenErrorResponse;
import com.lovehins.base.sdk.msg.auth.TokenForbiddenResponse;
import com.lovehins.base.sdk.util.ClientUtil;
import com.lovehins.base.sdk.vo.LogInfo;
import com.lovehins.base.sdk.vo.PermissionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-23 8:25
 */
@Configuration
@Slf4j
@SuppressWarnings("ALL")
public class AccessGatewayFilter implements GlobalFilter {
    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private ILogService logService;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    //    @Value("${zuul.prefix}")
//    private String zuulPrefix;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        log.info("check token and user permission....");
        ServerHttpRequest request = serverWebExchange.getRequest();
        final String requestUri = request.getPath().pathWithinApplication().value();
        final String method = request.getMethod().toString();
        BaseContextHandler.setToken(null);
        ServerHttpRequest.Builder mutate = request.mutate();

        // 输出路由、参数
        Map params = request.getQueryParams();
        log.info("####### {} - {} >> {}", method, requestUri, JSON.toJSONString(params));

        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = mutate.build();
            return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());
        }
        IJWTInfo user = null;
        try {
            user = getJWTUser(request, mutate);
        } catch (Exception e) {
            log.error("用户Token过期异常", e);
            return getVoidMono(serverWebExchange, new TokenForbiddenResponse("User Token Forbidden or Expired!"));
        }
        List<PermissionInfo> permissionIfs = userService.getAllPermissionInfo();
        // 判断资源是否启用权限约束
        Stream<PermissionInfo> stream = getPermissionIfs(requestUri, method, permissionIfs);
        List<PermissionInfo> result = stream.collect(Collectors.toList());
        PermissionInfo[] permissions = result.toArray(new PermissionInfo[]{});
        if (permissions.length > 0) {
            if (checkUserPermission(permissions, serverWebExchange, user)) {
                return getVoidMono(serverWebExchange, new TokenForbiddenResponse("User Forbidden!Does not has Permission!"));
            }
        }
        // 申请客户端密钥头
        mutate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        ServerHttpRequest build = mutate.build();
        return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());

    }

    /**
     * 网关抛异常
     *
     * @param body
     */
    @NotNull
    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, BaseResponse body) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


    /**
     * 获取目标权限资源
     *
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return
     */
    private Stream<PermissionInfo> getPermissionIfs(final String requestUri, final String method, List<PermissionInfo> serviceInfo) {
        return serviceInfo.parallelStream().filter(new Predicate<PermissionInfo>() {
            @Override
            public boolean test(PermissionInfo permissionInfo) {
                String url = permissionInfo.getUri();
                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url + "/"))
                        && method.equals(permissionInfo.getMethod());
            }
        });
    }

    private void setCurrentUserInfoAndLog(ServerWebExchange serverWebExchange, IJWTInfo user, PermissionInfo pm) {
        String host = ClientUtil.getClientIp((HttpServletRequest) serverWebExchange.getRequest());
        LogInfo logInfo = new LogInfo(pm.getMenu(), pm.getName(), pm.getUri(), new Date(), user.getId(), user.getName(), host);
        DBLog.getInstance().setLogService(logService).offerQueue(logInfo);
    }

    /**
     * 返回session中的用户信息
     *
     * @param request
     * @param ctx
     * @return
     */
    private IJWTInfo getJWTUser(ServerHttpRequest request, ServerHttpRequest.Builder ctx) throws Exception {
        List<String> strings = request.getHeaders().get(userAuthConfig.getTokenHeader());
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0);
        }
        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }
        ctx.header(userAuthConfig.getTokenHeader(), authToken);
        BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }


    private boolean checkUserPermission(PermissionInfo[] permissions, ServerWebExchange ctx, IJWTInfo user) {
        List<PermissionInfo> permissionInfos = userService.getPermissionByUsername(user.getUniqueName());
        PermissionInfo current = null;
        for (PermissionInfo info : permissions) {
            boolean anyMatch = permissionInfos.parallelStream().anyMatch(new Predicate<PermissionInfo>() {
                @Override
                public boolean test(PermissionInfo permissionInfo) {
                    return permissionInfo.getCode().equals(info.getCode());
                }
            });
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            return true;
        } else {
            if (!RequestMethod.GET.toString().equals(current.getMethod())) {
                setCurrentUserInfoAndLog(ctx, user, current);
            }
            return false;
        }
    }


    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private Mono<Void> setFailedRequest(ServerWebExchange serverWebExchange, String body, int code) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        return serverWebExchange.getResponse().setComplete();
    }

}