package com.lovehins.sidecar.client.demo.client;

<<<<<<< HEAD:sidecar/sidecar-client-demo/src/main/java/com/lovehins/sidecar/client/demo/client/PythonFeignClient.java
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lovehins.sidecar.client.demo.entity.Message;
=======
import com.github.wxiaoqi.security.sidecarclient.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> upstream/master:ace-sidecar/ace-sidecar-client-demo/src/main/java/com/github/wxiaoqi/security/sidecarclient/client/PythonFeignClient.java
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author yangyongjie
 * @create 2017-10-22 20:30
 */
@FeignClient(name = "sidecar-server")
public interface PythonFeignClient {
    //parse param like /message?id=12
    @RequestMapping("/message/{id}")
    List<Message> getMsg(@RequestParam("id") Long id);
    //parse url like /test
    @RequestMapping("/test")
    String getTest();
}