# Springboot2.0 整合 Dubbo入门


## **一、运行工具与环境**

运行环境：JDK 8，Maven 3.3+
技术栈：SpringBoot 2.0+、Dubbo 2.6+、ZooKeeper 3.3+
工具：IntelliJ IDEA、谷歌浏览器

## **二、Springboot快速集成Dubbo关键的依赖**

```maven
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>0.2.0</version>
</dependency>
```

## **三、如何使用**

**1、先启动DubboProviderApplication，如下所示：**

![image-20220221164328943](https://gitee.com/workingonescape/imagesformac/raw/master/images/202202211643085.png)

**2、借助于zookeeper客户端，可以看到HelloService服务已经注册到了注册中心，如下所示：**

![image-20220221164140746](https://gitee.com/workingonescape/imagesformac/raw/master/images/202202211641156.png)

**3、接着启动DubboConsumerApplication，如下所示：**

![image-20220221164253579](https://gitee.com/workingonescape/imagesformac/raw/master/images/202202211642630.png)

**4、调用sayHello接口，获取接口返回值**：

![image-20220221164424489](https://gitee.com/workingonescape/imagesformac/raw/master/images/202202211644639.png)

![image-20220221164905943](https://gitee.com/workingonescape/imagesformac/raw/master/images/202202211649029.png)
