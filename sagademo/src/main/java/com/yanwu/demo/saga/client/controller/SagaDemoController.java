package com.yanwu.demo.saga.client.controller;

import com.yanwu.demo.pojo.pojo.DemoClientPojo;
import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wm
 * @date 2018-09-03 15:08.
 * <p>
 * description:
 */
@RestController
@RequestMapping(value = "/demo/saga")
@RestSchema(schemaId = "sagaDemoController")
public class SagaDemoController {

    @Autowired
    private RestTemplate restTemplate;
    /**
     *
     * @param pojo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/create")
    @SagaStart(timeout = 100000)
    public int create(@RequestBody DemoServerPojo pojo) throws Exception {
        System.out.println("========== saga client demo create pojo ==========");
        Integer index = restTemplate.postForObject("cse://saga-server/server/saga/create", pojo, Integer.class);
        System.out.println("server-copy");
        Integer index2 = restTemplate.postForObject("cse://saga-copy/copy/saga/create", pojo, Integer.class);
        System.out.println("========== saga client demo create result ==========");
        return 0;
    }


}
