package com.reece.consumer.properties;

import lombok.Data;

/**
 * @author Reece
 * @createTime 2022/03/05 13:40:23
 * @Description
 */
@Data
public class ZookeeperProperties {


	private String uri;

	private Integer sessionTimeoutMs;
	private Integer connectionTimeoutMs;

	private Integer baseSleepTimeMs;

	private Integer maxRetries;
}
