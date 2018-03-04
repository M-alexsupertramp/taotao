package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.pojo.Content;

@Service
public class IndexService {

	@Autowired
	private ApiService apiService;
	
	@Value("${TAOTAO_MANAGE_BASE_URL}")
	private String TAOTAO_MANAGE_BASE_URL;
	
	@Value("${INDEX_AD_BASE_PATH}")
	private String INDEX_AD_BASE_PATH;
	
	@Value("${INDEX_AD1_ID}")
	private String INDEX_AD1_ID;
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public String queryIndexAD1() {
		try {
			// 1）查询后台广告数据
			String json = this.apiService.doGet(TAOTAO_MANAGE_BASE_URL + INDEX_AD_BASE_PATH.replace("{cid}", INDEX_AD1_ID));
			// 2）把json反序列化成对象
			List<Content> list = mapper.readValue(json, new TypeReference<List<Content>>() {
			});
			// 3）准备一个List<Map>用来保存正确的数据格式
			List<Map<String,Object>> result = new ArrayList<>();
			// 4）把List<Content>的值，转到map去
			for (Content c : list) {
				Map<String,Object> map = new HashMap<>();
				map.put("srcB", c.getPic2());
				map.put("height", 240);
				map.put("alt", c.getTitle());
				map.put("width", 670);
				map.put("src", c.getPic());
				map.put("widthB", 550);
				map.put("href", c.getUrl());
				map.put("heightB", 240);
				result.add(map);
			}
			return mapper.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
