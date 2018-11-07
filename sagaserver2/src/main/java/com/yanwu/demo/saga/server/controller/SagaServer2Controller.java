package com.yanwu.demo.saga.server.controller;

import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import com.yanwu.demo.saga.server.dao.mapper.DemoServerMapper;
import com.yanwu.demo.saga.server.dao.model.DemoServer;
import com.yanwu.demo.saga.server.service.SagaServerService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XuBaofeng.
 * @date 2018-09-03 15:08.
 * <p>
 * description:
 */
@RestController
@RequestMapping(value = "/server2/saga")
@RestSchema(schemaId = "sagaServer2")
public class SagaServer2Controller {

    @Autowired
    private SagaServerService sagaServerService;

    /**
     * 在转入事务中添加 @SagaStart 注解
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/create")
    public int create(@RequestBody DemoServerPojo pojo) throws Exception {
        System.out.println("========== saga server2 demo create pojo ==========");
        DemoServer demoServer = new DemoServer();
        demoServer.setServerName(pojo.getServerName());
        demoServer.setServerPassword(pojo.getServerPassword());
        int result = sagaServerService.create(demoServer);
        System.out.println("========== saga server2 demo create result: " + result + " ==========");
        return result;
    }


}