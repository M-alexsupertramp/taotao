package com.taotao.search.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.search.pojo.Item;
@Service
public class ItemService {
   @Value("${TAOTAO_MANAGER_BASE_PATH}")
	private String TAOTAO_MANAGER_BASE_PATH;
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	public Item queryItemById(long itemId) {
		try {
			String uri=TAOTAO_MANAGER_BASE_PATH+"/rest/api/item/"+itemId;
			String jsonData = apiService.doGet(uri);
			//解析数据
			Item item = MAPPER.readValue(jsonData, Item.class);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
