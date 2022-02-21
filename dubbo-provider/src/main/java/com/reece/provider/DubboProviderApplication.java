package com.reece.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务提供启动类
 * @author Reece
 * @date 2022/2/21 16:34:45
 */
@EnableDubbo(scanBasePackages = "com.reece.provider.service.impl")
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
