package com.reece.provider.dubbocontainer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Reece
 * @createTime 2022/03/09 01:06:16
 * @Description
 */
@Slf4j
@Component
@Order(100)
public class DubboProviderContainer implements ApplicationContextAware {


	private ApplicationContext context;


	public ServiceRepository getServiceRepository() {
		return context.getBean(ServiceRepository.class);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
