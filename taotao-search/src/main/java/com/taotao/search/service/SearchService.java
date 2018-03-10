package com.taotao.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.pojo.Item;
import com.taotao.search.vo.PageResult;

@Service
public class SearchService {

	 @Autowired
	 private HttpSolrServer server;

	public PageResult<Item> search(String keyword, Integer page, Integer rows) {
		try {
			//搜索的关键词,--如果是空,则给个默认值,推荐商品
			if(StringUtils.isBlank(keyword)){
				keyword="*";
			}
			//查询条件
			 SolrQuery query = new SolrQuery("title:" + keyword);
			
			 // 设置分页,page至少要>=1
	        query.setStart((Math.max(page, 1) - 1) * rows);
	        query.setRows(rows);
			 
			 //判断是否需要高亮
	        boolean isHighlight = !StringUtils.equals("*", keyword);
			
	        if(isHighlight){
	        	//开启高亮
	        	query.setHighlightSimplePre("<em>");
	        	query.setHighlightSimplePost("/<em>");
	        	query.addHighlightField("title");
	        }
	        
	        //查询并获取响应
	       
				QueryResponse response = server.query(query);
				//获取非高亮结果
				List<Item> items = response.getBeans(Item.class);
				//获取高亮结果
				if(isHighlight){
					Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
					for (Item item : items) {
						Map<String, List<String>> map = highlighting.get(item.getId().toString());
						String string = map.get("title").get(0);
						item.setTitle(string);
					}
				}
				return new PageResult<>(response.getResults().getNumFound(),items);
				
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
			return  new PageResult<>(0l,new ArrayList<Item>(0));
	}
	
	
	
}
