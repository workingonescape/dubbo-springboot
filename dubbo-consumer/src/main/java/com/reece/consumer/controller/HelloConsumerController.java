package com.reece.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.reece.api.service.HelloService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Reece
 * @date 2022/2/21 16:33:49
 */
@RestController
public class HelloConsumerController {

    @Reference
    private HelloService helloService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return helloService.sayHello(name);
    }

}
