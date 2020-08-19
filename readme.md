# 该项目主要用于实现sentinel的三种不通方式实现降级、限流、熔断操作

1. 手动实现限流熔断机制,配合RuleConfig规则（此方式适用于springmvc项目，但引入jar包需调整）

2. 注解@SentinelResource实现限流熔断机制

3. 无需侵入代码实现限流熔断机制；在sentinel-dashboard服务端配置即可


借鉴url：[sentinel-demo](https://github.com/alibaba/Sentinel/tree/master/sentinel-demo)

###具体实现步骤：
1、pom文件引入sentinel
```python
<dependency>
   <groupId>com.alibaba.cloud</groupId>
   <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```


2、yml文件添加配置
```python
  spring:
    application:
      name: springboot-sentinel
    cloud:
      sentinel:
        transport:
          dashboard: localhost:8080
  
  server:
    port: 8082
```    
    
3、启动sentinel-dashboard服务端项目
```python
java -jar sentinel-dashboard-1.7.2.jar
```
4、编写核心代码，具体见 com.study.sentinel.controller.SentinelController

5、并发测试，可采用jmeter进行并发测试

6、查看执行结果以及dashboard控制台日志
