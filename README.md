# transaction-saga-demo

#### 项目介绍
基于service comb和saga实现的分布式事务
 

#### 使用说明

一:从GitHub上将saga的代码拉到本地
https://github.com/apache/incubator-servicecomb-saga.git进入到对应目录中,将saga打入本地maven仓库
1.构建可执行文件：
mvn clean install -DskipTests
2.构建可执行文件和docker镜像：
mvn clean install -DskipTests -Pdocker
3.构建可执行文件以及Saga发行包
mvn clean install -DskipTests -Prelease
在执行以上任一指令后, 可在 alpha/alpha-server/target/saga/alpha-server-${version}-exec.jar中找到alpha-server的可执行文件

二:运行alpha-server
使用mysql|PostgreSQL作为alpha的数据库:
1.添加mysql-connector-java依赖到alpha/alpha-server/pom.xml中(PostgreSQL跳过)
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

2.完成一步骤,找到可执行文件alpha/alpha-server/target/saga/alpha-server-
${version}-exec.jar

3.运行DB
a.运行mysql
docker run -d -e "MYSQL_ROOT_PASSWORD=password" -e "MYSQL_DATABASE=saga" -e "MYSQL_USER=saga" -e "MYSQL_PASSWORD=password" -p 3306:3306 mysql/mysql-server:5.7

4. 创建一个名为saga的数据库

5. 运行alpha
 1> 通过docker
docker run -d -p 8080:8080 -p 8090:8090 -e "JAVA_OPTS=-Dspring.profiles.active=mysql -Dspring.datasource.url=jdbc:mysql://${host_address}:3306/saga?useSSL=false" alpha-server:${saga_version}
 2> 通过可执行文件 更改数据库配置
### spring.profiles.active >> saga使用的数据库类型
### spring.datasource.url >> mysql链接
### spring.datasource.username >> mysql用户
### spring.datasource.password >> 密码

mysql：
java -Dspring.profiles.active=mysql 
-Dspring.datasource.url=jdbc:mysql://localhost:3306/saga?useSSL=false -
Dspring.datasource.username=root -Dspring.datasource.password=xbf12138 -jar 
alpha-server-0.3.0-SNAPSHOT-exec.jar

postgresql：
java -Dspring.profiles.active=prd -Dspring.datasource.url=jdbc:postgresql://localhost:5432/saga?useSSL=false -Dspring.datasource.username=postgres -Dspring.datasource.password=x5 -jar alpha-server-0.3.0-SNAPSHOT-exec.jar
注意:默认情况下，端口8080用于通过RPC提供omega的请求，而端口8090用于查询存储在alpha中的事件。

三:使用saga
1.在项目中引入maven依赖
<saga.version>0.3.0-SNAPSHOT</saga.version>
 <dependency>
    <groupId>org.apache.servicecomb.saga</groupId>
    <artifactId>omega-spring-starter</artifactId>
    <version>${saga.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.servicecomb.saga</groupId>
    <artifactId>omega-transport-resttemplate</artifactId>
    <version>${saga.version}</version>
</dependency>
2.添加saga注解以及相应的补偿方法
a. 在应用的入口添加 @EnableOmega 注解来初始化omega的配置并与alpha建立连接
@SpringBootApplication
@EnableOmega
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
b. 在全局事务的起点添加@SagaStart注解
@SagaStart(timeout=10)
public boolean transferMoney(String from, String to, int amount) {
  transferOut(from, amount);
  transferIn(to, amount);
}
c. 在子事务处添加@Compensable注解, 并指明对其的补偿方法，参数保持一致
@Compensable(timeout=5, compensationMethod="cancel")
public boolean transferOut(String from, int amount) {
  repo.reduceBalanceByUsername(from, amount);
}
// 补偿方法
public boolean cancel(String from, int amount) {
  repo.addBalanceByUsername(from, amount);
}
d. 对转入的服务重复第三步即可
3. 从saga-0.3.0, 可以在服务函数或者取消函数中通过访问OmegaContext来获取gloableTxld以及localTxld信息
注意:
 1> 实现的服务和补偿必须满足幂等的条件
 2> 默认情况下, 超时设置需要显式声明才生效
 3> 若全局事务起点与子事务起点重合, 需要同时声明@SagaStart注解和@Compensable注解

五:配置omega,在application.yaml中添加一下配置
1.添加alpha集群地址
a.application.properties
alpha.cluster.address=127.0.0.1:8080
b.microservice.yaml
     spring:
        application:
           name: {application.name}
alpha:
        cluster:
          address: {alpha.cluster.addresses}
2.添加消费者、提供者
  #Add Saga Handler
  handler:
    chain:
      Consumer:
        default: loadbalance,saga-consumer

  servicecomb:
  #Add Saga Handler
    handler:
      chain:
        Provider:
          default: saga-provider

六:通过访问http://${alpha-server:port}/events来获取所有的saga事件信息。

七:过程中的bug
1.@compensable  得不到GlobalTxId  NPE
  SAGA依赖不可少，另下面的依赖注意版本
   <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>20.0</version>
   </dependency>
2.内存溢出
  项目不要直接导入saga包
3.状态  UNKNOED
补偿与业务匹配
4.编译 alpha 失败
 
