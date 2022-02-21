package com.reece.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.reece.api.service.HelloService;

/**
 * HelloServiceImpl
 * @author Reece
 * @date 2022/2/21 16:34:26
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
