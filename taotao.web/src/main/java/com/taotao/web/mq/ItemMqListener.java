package com.taotao.web.mq;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.PropertiesService;

public class ItemMqListener {
	@Autowired
	private ItemService itemService;
	public void getMsg(String msg){
		if(StringUtils.isNotEmpty(msg)){
			try {
				JsonNode jsonNode = PropertiesService.MAPPER.readTree(msg);
				//操作类型
				String type = jsonNode.get("type").asText();
				//操作的商品对象
				long itemId = jsonNode.get("data").asLong();
				//如果是更新了商品信息或者删除了商品信息
				if(StringUtils.equals("update", type) || StringUtils.equals("delete", type)){
					//清除对应商品的缓存
					itemService.clearRedisCacheByItemId(itemId);
				}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
}
