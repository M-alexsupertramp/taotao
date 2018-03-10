package com.taotao.search.mq.listener;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

/**
 * 监听后台系统发送的消息并处理
 * @author Mary
 *
 */
public class ItemMessageListener {
	@Autowired
	private HttpSolrServer server;
	@Autowired
	private ItemService itemService;
	private static final ObjectMapper MAPPER=new ObjectMapper();
	public void consume(String msg){
		try {
			//解析消息
			JsonNode jsonNode = MAPPER.readTree(msg);
			long itemId = jsonNode.get("itemId").asLong();
			String type = jsonNode.get("type").asText();
			//判断操作类型
			if(StringUtils.equals("insert", type) || StringUtils.equals("update", type)){
				//根据id查询商品
				Item item=itemService.queryItemById(itemId);
				if(item!=null){
					//新增数据到solr
					server.addBean(item);
					server.commit();
				}
			}else if(StringUtils.equals("delete", type)){
				//删除solr数据
				server.deleteById(Long.toString(itemId));
				server.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
