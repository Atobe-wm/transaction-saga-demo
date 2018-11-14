package com.yanwu.demo.saga.client.consumer;

import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author XuBaofeng.
 * @date 2018-09-03 15:47.
 * <p>
 * description:
 */
@Component
public class Saga1Consumer {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * saga子事务, 调用saga-server的create方法, 添加 @Compensable 注释
     * timeout: 超时时间, 当请求超过超时时间时, 执行补偿措施
     * compensationMethod: 补偿措施, 当事务出现超时或异常等需要回滚的情况时, 触发该方法
     *
     * @param demoServerPojo
     * @return
     */
    public int sageServerCreate(DemoServerPojo demoServerPojo) {
        System.out.println("========== sage copy create ==========");
        Integer index = restTemplate.postForObject("cse://saga-server/server/saga/create", demoServerPojo, Integer.class);
        System.out.println("========== sage copy create result: " + index + " ==========");
        return index;
    }


}
