package com.reece.consumer.config;

import com.reece.consumer.properties.ZookeeperBuilder;
import com.reece.consumer.properties.ZookeeperProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * @author Reece
 * @createTime 2022/03/05 13:38:57
 * @Description
 */
@Configuration
@ConditionalOnClass({CuratorFramework.class, ZooKeeper.class})
@Order(100)
public class ZookeeperConfiguration implements ApplicationContextAware {


	private CuratorFramework client = null;
	private ApplicationContext applicationContext;

	@Bean
	public ZookeeperProperties zookeeperProperties() {
		ZookeeperProperties properties = new ZookeeperProperties();
		Environment environment = applicationContext.getEnvironment();
		properties.setUri(environment.getProperty("zookeeper.connection.zkServer"));
		properties.setSessionTimeoutMs(environment.getProperty("zookeeper.curator.sessionTimeoutMs",Integer.class));
		properties.setConnectionTimeoutMs(environment.getProperty("zookeeper.curator.connectionTimeoutMs",Integer.class));
		properties.setBaseSleepTimeMs(environment.getProperty("zookeeper.curator.baseSleepTimeMs",Integer.class));
		properties.setMaxRetries(environment.getProperty("zookeeper.curator.maxRetries",Integer.class));
		return properties;
	}

	@Bean
	public CuratorFramework curatorFramework() throws Exception {
		if (client == null) {
			client = ZookeeperBuilder.buildCuratorFramework(zookeeperProperties());
		}
		return client;
	}



	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
