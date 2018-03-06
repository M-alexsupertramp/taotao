package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PropertiesService {
	@Value("${IMAGE_PATH}")
	public String TAOTAO_MANAGE_IMAGE_PATH;
	@Value("${IMAGE_URL}")
	public String TAOTAO_MANAGE_IMAGE_URL;
	
	@Value("${REDIS_ITEM_CAT_ALL_KEY}")
	public String REDIS_ITEM_CAT_ALL_KEY;
	@Value("${REDIS_ITEM_CAT_AKK_TIME}")
	public Integer REDIS_ITEM_CAT_AKK_TIME;
	 //前台要求传回json数据，定义json处理对象,将java对象转成json返回
	public static final ObjectMapper MMAPPER = new ObjectMapper();
	
}
