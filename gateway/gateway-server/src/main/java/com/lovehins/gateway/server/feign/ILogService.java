<<<<<<< HEAD:gateway/gateway-server/src/main/java/com/lovehins/gateway/server/feign/ILogService.java
package com.lovehins.gateway.server.feign;

import com.lovehins.base.sdk.vo.LogInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-07-01 15:16
 */
@FeignClient("web-admin")
public interface ILogService {
  @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
  public void saveLog(LogInfo info);
}
=======
package com.github.wxiaoqi.security.gate.feign;

import com.github.wxiaoqi.security.api.vo.log.LogInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-07-01 15:16
 */
@FeignClient("ace-admin")
public interface ILogService {
  @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
  public void saveLog(LogInfo info);
}
>>>>>>> upstream/master:ace-gate/ace-gate-server/src/main/java/com/github/wxiaoqi/security/gate/feign/ILogService.java
