package com.lovehins.sidecar.client.demo.rest;

import com.lovehins.sidecar.client.demo.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.lovehins.sidecar.client.demo.client.PythonFeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author yangyongjie
 * @create 2017-10-22 20:30
 */
@RestController
@RequestMapping("test")
public class PythonController {
    @Autowired
    private  PythonFeignClient pythonFeignClient;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        return pythonFeignClient.getTest();
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public List<Message> getMsg(@PathVariable Long id) {
        return pythonFeignClient.getMsg(id);
    }
}