package com.yanwu.demo.saga.client.consumer;

import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import com.yanwu.demo.saga.client.dao.mapper.DemoClientMapper;
import com.yanwu.demo.saga.client.dao.model.DemoClientExample;
import org.apache.servicecomb.saga.omega.transaction.annotations.Compensable;
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
     * @param pojo
     * @return
     */
    public int sageServerCreate(DemoServerPojo pojo) {
        System.out.println("========== sage copy create ==========");
        Integer index = restTemplate.postForObject("cse://saga-server/server/saga/create", pojo, Integer.class);
        System.out.println("========== sage copy create result: " + index + " ==========");
        return index;
    }


}
