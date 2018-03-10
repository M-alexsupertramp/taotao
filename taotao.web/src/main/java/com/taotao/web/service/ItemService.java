package com.taotao.web.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.common.service.RedisService;
import com.taotao.web.pojo.Item;
import com.taotao.web.vo.ItemVo;
@Service
public class ItemService {

	@Autowired
	private ApiService apiService;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PropertiesService propertiesService;
	
	//查询商品信息的缓存key
	@Value("${REDIS_ITEM_KEY}")
	public String REDIS_ITEM_KEY;
	//查询商品信息的缓存存活时间
	@Value("${REDIS_ITEM_TIME}")
	private Integer REDIS_ITEM_TIME;
	
	
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	//根据id查询商品详情
	public ItemVo queryById(Long itemId) {
		String key=REDIS_ITEM_KEY+itemId;
		//尝试从缓存命中
		try {
			String jsonData = redisService.get(key);
			if(StringUtils.isNoneEmpty(jsonData)){
				//如果命中则直接返回结果
				return MAPPER.readValue(jsonData, ItemVo.class);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String url=propertiesService.MANAGE_TAOTAO_BASE_URL+propertiesService.ITEM_PATH.replace("{itemId}", itemId.toString());
			String json=apiService.doGet(url);
			ItemVo item = MAPPER.readValue(json, ItemVo.class);
			
			try {
				redisService.set(key,MAPPER.writeValueAsString(json), REDIS_ITEM_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return item;
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void clearRedisCacheByItemId(long itemId) {
		//清空redis数据
		redisService.del(REDIS_ITEM_KEY+itemId);
	}
	
}
