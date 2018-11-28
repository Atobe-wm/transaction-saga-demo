package com.yanwu.demo.saga.client.service.impl;

import com.yanwu.demo.pojo.pojo.DemoServerPojo;
import com.yanwu.demo.saga.client.consumer.Saga1Consumer;
import com.yanwu.demo.saga.client.dao.mapper.DemoClientMapper;
import com.yanwu.demo.saga.client.dao.model.DemoClient;
import com.yanwu.demo.saga.client.dao.model.DemoClientExample;
import com.yanwu.demo.saga.client.service.SagaClientService;
import org.apache.servicecomb.saga.omega.transaction.annotations.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author XuBaofeng.
 * @date 2018-09-03 15:08.
 * <p>
 * description:
 */
@Service
public class SagaClientServiceImpl  implements SagaClientService{

    @Autowired
    private DemoClientMapper demoClientMapper;

    @Autowired
    private Saga1Consumer saga1Consumer;

    /**
     * 该方法调用saga-server的create方法, 我们在saga-server的create方法中创建一个除零异常
     * 用来测试当saga-server的create方法失败是, 该事务是否会滚
     * 一: 添加saga分布式事务之前: saga-client的保存操作会成功, saga-server的保存操作会因为异常而失败
     * 但是saga-client并没有因为saga-server的异常而回滚, 此时没有保证数据最终一致性, 所以saga-client保存的数据为脏数据
     * <p/>
     * 二: 添加saga分布式事务之后: saga-server的保存操作会等待saga-server的操作结果
     * 当saga-client保存或访问saga-client超时, 则会直接回滚事务, 保证了事务的最终一致性
     * ===== @SagaStart: saga事务的起点, timeout: 当前事务的超时时间, 当超过超时时间时, 会触发补偿业务
     *
     * @param demoClient
     * @return
     * @throws Exception
     */
    @Override
   @Transactional(rollbackFor = Exception.class)
    @Compensable(timeout = 800, compensationMethod = "createRollback")
    public int create(DemoClient demoClient) throws Exception {
        // ===== 保存saga-client entity
        int insert = demoClientMapper.insert(demoClient);
        // ===== 通过微服务调用保存 saga-copy entity
        DemoServerPojo serverPojo = new DemoServerPojo();
        serverPojo.setServerName(demoClient.getClientName());
        serverPojo.setServerPassword(demoClient.getClientPassword());
        saga1Consumer.sageServerCreate(serverPojo);
        return insert;
    }

    /**
     * 事务回滚的补偿措施
     * 1. 必须和被 @Compensable 注解的方法在同一个类
     * 2. 参数必须一致
     * 3. 补偿业务的方法名必须和 compensationMethod 的值一致
     *
     * @param demoClient
     * @return
     */
    public int createRollback(DemoClient demoClient) {
        System.out.println("========== create client rollback ==========");
        DemoClientExample example = new DemoClientExample();
        DemoClientExample.Criteria criteria = example.createCriteria();
        criteria.andClientNameEqualTo(demoClient.getClientName());
        criteria.andClientPasswordEqualTo(demoClient.getClientPassword());
        return demoClientMapper.deleteByExample(example);
    }

}
