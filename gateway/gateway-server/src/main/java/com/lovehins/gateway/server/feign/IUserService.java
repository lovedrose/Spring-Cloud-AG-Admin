<<<<<<< HEAD:gateway/gateway-server/src/main/java/com/lovehins/gateway/server/feign/IUserService.java
package com.lovehins.gateway.server.feign;

import com.lovehins.gateway.server.config.FeignConfiguration;
import com.lovehins.base.sdk.vo.PermissionInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "web-admin",configuration = FeignConfiguration.class)
public interface IUserService {
  @RequestMapping(value="/api/user/un/{username}/permissions",method = RequestMethod.GET)
//  @RequestLine(value = "GET /api/user/un/{username}/permissions")
  public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);
//  @RequestLine(value = "GET /api/permissions")
  @RequestMapping(value="/api/permissions",method = RequestMethod.GET)
  List<PermissionInfo> getAllPermissionInfo();
}
=======
package com.github.wxiaoqi.security.gate.feign;

import com.github.wxiaoqi.security.api.vo.authority.PermissionInfo;
import com.github.wxiaoqi.security.gate.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "ace-admin",fallback = UserServiceFallback.class)
public interface IUserService {
  @RequestMapping(value="/api/user/un/{username}/permissions",method = RequestMethod.GET)
  public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);
  @RequestMapping(value="/api/permissions",method = RequestMethod.GET)
  List<PermissionInfo> getAllPermissionInfo();
}
>>>>>>> upstream/master:ace-gate/ace-gate-server/src/main/java/com/github/wxiaoqi/security/gate/feign/IUserService.java
