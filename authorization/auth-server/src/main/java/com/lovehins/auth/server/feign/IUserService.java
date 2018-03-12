package com.lovehins.auth.server.feign;

import com.lovehins.auth.server.configuration.FeignConfiguration;
import com.lovehins.base.sdk.vo.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "web-admin",configuration = FeignConfiguration.class)
public interface IUserService {
  @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
  UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}