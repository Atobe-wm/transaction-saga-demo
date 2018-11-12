package com.yanwu.demo.saga.client.controller;

import com.yanwu.demo.pojo.pojo.DemoClientPojo;
import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import com.yanwu.demo.saga.client.consumer.Saga1Consumer;
import com.yanwu.demo.saga.client.consumer.Saga2Consumer;
import com.yanwu.demo.saga.client.dao.model.DemoClient;
import com.yanwu.demo.saga.client.dao.model.DemoClientEntity;
import com.yanwu.demo.saga.client.service.SagaClientService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author XuBaofeng.
 * @date 2018-09-03 15:08.
 * <p>
 * description:
 */
@RestController
@RequestMapping(value = "/client/saga")
@RestSchema(schemaId = "sagaClient")
public class SagaClientController {

    @Autowired
    private SagaClientService sagaClientService;
    @Autowired
    private Saga1Consumer saga1Consumer;
    @Autowired
    private Saga2Consumer saga2Consumer;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * client 有数据库操作
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/create")
    @SagaStart(timeout = 100000)
    public int create(@RequestBody DemoClientPojo pojo) throws Exception {
        System.out.println("========== saga client demo create pojo ==========");
        DemoClient demoClient = new DemoClient();
        demoClient.setClientName(pojo.getClientName());
        demoClient.setClientPassword(pojo.getClientPassword());
        int result = sagaClientService.create(demoClient);
        System.out.println("========== saga client demo create result ==========");
        return result;
    }

    /**
     * client 无数据库操作
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/invoke")
    @SagaStart(timeout = 100000)
    public int invoke(@RequestBody DemoClientPojo pojo) throws Exception {
        DemoServerPojo demoServerPojo = new DemoServerPojo();
        demoServerPojo.setServerName(pojo.getClientName());
        demoServerPojo.setServerPassword(pojo.getClientPassword());
        Integer index = restTemplate.postForObject("cse://saga-copy/copy/saga/create", pojo, Integer.class);
       // saga1Consumer.sageServerCreate(demoServerPojo);
        //saga2Consumer.sageServerCreate(demoServerPojo);
        return 0;
    }

}
