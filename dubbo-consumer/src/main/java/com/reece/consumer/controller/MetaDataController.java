package com.reece.consumer.controller;


import com.reece.consumer.util.ZookeeperClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reece
 * @createTime 2022/03/05 13:51:44
 * @Description
 */
@RestController
@RequestMapping("/dubbo/metadata")
@RequiredArgsConstructor
public class MetaDataController {


	private final ZookeeperClientUtils zookeeperClientUtils;


	@RequestMapping("/getMetaData")
	@ResponseBody
	public String getMetaData() {
		return zookeeperClientUtils.getMetaData();
	}

}
