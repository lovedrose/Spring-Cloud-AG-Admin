<<<<<<< HEAD:authorization/auth-server/src/main/java/com/lovehins/auth/server/feign/IUserService.java
package com.lovehins.auth.server.feign;

import com.github.wxiaoqi.security.api.vo.user.UserInfo;
import com.lovehins.auth.server.configuration.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "web-admin",configuration = FeignConfiguration.class)
public interface IUserService {
  @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
  public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}
=======
package com.github.wxiaoqi.security.auth.feign;

import com.github.wxiaoqi.security.api.vo.user.UserInfo;
import com.github.wxiaoqi.security.auth.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "ace-admin",configuration = FeignConfiguration.class)
public interface IUserService {
  @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
  public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}
>>>>>>> upstream/master:ace-auth/ace-auth-server/src/main/java/com/github/wxiaoqi/security/auth/feign/IUserService.java
