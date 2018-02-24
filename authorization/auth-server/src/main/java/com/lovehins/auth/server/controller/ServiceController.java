package com.lovehins.auth.server.controller;

import com.lovehins.auth.server.biz.ClientBiz;
import com.lovehins.auth.server.entity.Client;
import com.lovehins.auth.server.entity.ClientService;
import com.lovehins.base.sdk.msg.ObjectRestResponse;
import com.lovehins.base.sdk.rest.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lovedrose
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz,Client>{

    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable int id, String clients){
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse().rel(true);
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<ClientService> getUsers(@PathVariable int id){
        return new ObjectRestResponse<ClientService>().rel(true).data(baseBiz.getClientServices(id));
    }
}
