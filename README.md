# transaction-saga-demo

#### 项目介绍
基于service comb和saga实现的分布式事务
 

#### 使用说明

###### 一: 从GitHub上将saga的代码拉到本地
   https://github.com/apache/incubator-servicecomb-saga.git进入到对应目录中, 将saga打入本地maven仓库
##### 1.只构建可执行文件：
   `mvn clean install -DskipTests`
##### 2.同时构建可执行文件和docker镜像：
   `mvn clean install -DskipTests -Pdocker`
##### 3.同时构建可执行文件以及Saga发行包
   `mvn clean install -DskipTests -Prelease`

在执行以上任一指令后, 可在 alpha/alpha-server/target/saga/alpha-server-${version}-exec.jar中找到alpha-server的可执行文件

###### 二: 使用saga
在项目中引入maven依赖

```
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
```

###### 三: 添加saga注解以及相应的补偿方法
##### 1. 在应用的入口添加 @EnableOmega 注解来初始化omega的配置并与alpha建立连接

```
@SpringBootApplication
@EnableOmega
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```
  
##### 2. 在全局事务的起点添加@SagaStart注解

```
@SagaStart(timeout=10)
public boolean transferMoney(String from, String to, int amount) {
  transferOut(from, amount);
  transferIn(to, amount);
}
```
  
##### 3. 在子事务处添加@Compensable注解, 并指明对其的补偿方法

```
@Compensable(timeout=5, compensationMethod="cancel")
public boolean transferOut(String from, int amount) {
  repo.reduceBalanceByUsername(from, amount);
}
// 补偿方法
public boolean cancel(String from, int amount) {
  repo.addBalanceByUsername(from, amount);
}
```
  
##### 4. 对转入的服务重复第三步即可
  
##### 5. 从saga-0.3.0, 可以在服务函数或者取消函数中通过访问OmegaContext 来获取gloableTxld以及localTxld信息
###### 注意:
   1) 实现的服务和补偿必须满足幂等的条件
   2) 默认情况下, 超时设置需要显式声明才生效
   3) 若全局事务起点与子事务起点重合, 需要同时声明@SagaStart注解和@Compensable注解

###### 四: 运行alpha-server
  使用mysql作为alpha的数据库:
##### 1.添加mysql-connector-java依赖到alpha/alpha-server/pom.xml中

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```
     
##### 2. 安装saga
   `mvn clean package -DskipTests`

然后,找到可执行文件alpha/alpha-server/target/saga/alpha-server-${version}xec.jar

##### 3.运行mysql
`docker run -d -e "MYSQL_ROOT_PASSWORD=password" -e "MYSQL_DATABASE=saga" -e "MYSQL_USER=saga" -e "MYSQL_PASSWORD=password" -p 3306:3306 mysql/mysql-serr:5.7`
  
##### 4. 创建一个名为saga的数据库, 提供给alpha-server使用

##### 5. 运行alpha
 1> 通过docker
`docker run -d -p 8080:8080 -p 8090:8090 -e "JAVA_OPTS=-Dspring.profiles.active=mysql -Dspring.datasource.url=jdbc:mysql://${host_address}:3306/saga?useSSL=false" alpha-server:${saga_version}`
 2> 通过可执行文件

```
spring.profiles.active >> saga使用的数据库类型
spring.datasource.url >> mysql链接
spring.datasource.username >> mysql用户
spring.datasource.password >> 密码
java -Dspring.profiles.active=mysql -Dspring.datasource.url=jdbc:mysql://localhost:3306/saga?useSSL=false -Dspring.datasource.username=root -Dspring.datasource.password=xbf12138 -jar alpha-server-0.3.0-SNAPSHOT-exec.jar
```

###### 注意:默认情况下，端口8080用于通过RPC提供omega的请求，而端口8090用于查询存储在alpha中的事件。

###### 五: 配置omega,在application.yaml中添加一下配置

```
spring:
  application:
    name: {application.name}
alpha:
  cluster:
    address: {alpha.cluster.addresses}
```

###### 六: 通过访问 http://${alpha-server:port}/events 来获取所有的saga事件信息。