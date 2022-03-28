package com.reece.consumer.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Reece
 * @createTime 2022/03/05 13:48:00
 * @Description
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ZookeeperClientUtils {


	private final CuratorFramework curatorFramework;


	public String getMetaData() {
		try {
			byte[] bytes = curatorFramework.getData().forPath("/dubbo/metadata/com.reece.api.service.HelloService/provider/dubbo-provider");
			return new String(bytes);
		} catch (Exception e) {
			log.error("getMetaData err, errMsg:", e);
		}
		return "";
	}

}
