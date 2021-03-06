package com.lovehins.auth.client.feign;

import com.lovehins.base.sdk.msg.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 服务鉴权 feign 接口
 */
@FeignClient(value = "${auth.serviceId}",configuration = {})
public interface ServiceAuthFeign {
    @RequestMapping(value = "/client/myClient")
    ObjectRestResponse<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId, @RequestParam("secret") String secret);
    @RequestMapping(value = "/client/token",method = RequestMethod.POST)
    ObjectRestResponse getAccessToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
    @RequestMapping(value = "/client/servicePubKey",method = RequestMethod.POST)
    ObjectRestResponse<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
    @RequestMapping(value = "/client/userPubKey",method = RequestMethod.POST)
    ObjectRestResponse<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

}
