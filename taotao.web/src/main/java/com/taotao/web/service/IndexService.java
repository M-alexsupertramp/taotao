package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class IndexService {

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private PropertiesService propertiesService;
	
	
	public String queryIndexAD1() {
		try {
			// 1）查询后台广告数据
			String json = this.apiService.doGet(propertiesService.TAOTAO_MANAGE_BASE_URL + propertiesService.INDEX_AD_BASE_PATH.replace("{cid}", propertiesService.INDEX_AD1_ID));
			// 2）把json反序列化成对象
			/*List<Content> list = PropertiesService.MAPPER.readValue(json, new TypeReference<List<Content>>() {
			});*/
			
			//解析json数据
			JsonNode jsonNode=PropertiesService.MAPPER.readTree(json);
			//获取row数据
			ArrayNode arrNode = (ArrayNode) jsonNode.get("rows");
			// 3）准备一个List<Map>用来封装要返回的数据格式
			List<Map<String,Object>> result = new ArrayList<>();
			// 4）把List<Content>的值，转到map去
			for (JsonNode jn : arrNode) {
				Map<String,Object> map = new HashMap<>();
				map.put("srcB", jn.get("pic2").asText());
				map.put("height", 240);
				map.put("alt", jn.get("title").asText());
				map.put("width", 670);
				map.put("src", jn.get("pic").asText());
				map.put("widthB", 550);
				map.put("href", jn.get("url").asText());
				map.put("heightB", 240);
				result.add(map);
			}
			//返回封装后的json数据
			return PropertiesService.MAPPER.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String queryIndexAD2() {
		try {
			// 1）查询后台广告数据
			String json = this.apiService.doGet(propertiesService.TAOTAO_MANAGE_BASE_URL + propertiesService.INDEX_AD_BASE_PATH.replace("{cid}", propertiesService.INDEX_AD2_ID));
			// 2）把json反序列化成对象
			/*List<Content> list = PropertiesService.MAPPER.readValue(json, new TypeReference<List<Content>>() {
			});*/
			
			//解析json数据
			JsonNode jsonNode=PropertiesService.MAPPER.readTree(json);
			//获取row数据
			ArrayNode arrNode = (ArrayNode) jsonNode.get("rows");
			// 3）准备一个List<Map>用来封装要返回的数据格式
			List<Map<String,Object>> result = new ArrayList<>();
			// 4）把List<Content>的值，转到map去
			for (JsonNode jn : arrNode) {
				Map<String,Object> map = new HashMap<>();
				map.put("srcB", jn.get("pic2").asText());
				map.put("height", 240);
				map.put("alt", jn.get("title").asText());
				map.put("width", 670);
				map.put("src", jn.get("pic").asText());
				map.put("widthB", 550);
				map.put("href", jn.get("url").asText());
				map.put("heightB", 240);
				result.add(map);
			}
			//返回封装后的json数据
			return PropertiesService.MAPPER.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
