package com.taotao.manage.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import com.taotao.common.service.ApiService;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
@Service
public class ItemService extends BaseService<Item> {
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 封装一个通用的发消息的方法
	 */
	private void sendMessage(Long itemId,String type){
		//发送消息到RabbitMQ
		try {
			Map<String, Object>msg=new HashMap<>();
			msg.put("itemId", itemId);
			msg.put("type", type);
			msg.put("date", System.currentTimeMillis());
			//发送
			rabbitTemplate.convertAndSend("item."+type, PropertiesService.MMAPPER.writeValueAsString(msg));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增商品和商品描述
	 * @param item
	 * @param desc
	 */
	public boolean save(Item item, String desc) {
		boolean flag=false;
		//设置item id为null,数据库主键自增
		item.setId(null);
		//设置商品状态，1-正常，2-下架，3-删除,
		item.setStatus(1);
		//新增商品
		Integer result = super.save(item);
		if(result==1){
			//创建商品描述对象
			ItemDesc itemDesc = new ItemDesc();
			//设置商品id
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			//新增商品描述对象
			Integer update = itemDescService.save(itemDesc);
			if(update==1){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * 分页查询并排序
	 */
	public PageInfo<Item> queryPageListAndSort(Integer page, Integer rows,
			String orderByClause) {
		return	super.queryPageListAndSort(page, rows, orderByClause);
	}

	/**
	 * 更新商品和商品描述
	 * @param item
	 * @param desc
	 * @return
	 */
	public boolean updateItemAndItemDesc(Item item, String desc) {
		//强制设置不能修改的字段为null,例如status
		boolean flag=false;
		item.setStatus(null);
		
		//修改商品
		Integer updateItem = super.updateSelective(item);
		if(updateItem==1){
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			Integer updateItemDesc = itemDescService.updateSelective(itemDesc);
			if(updateItemDesc==1){
				//rabbitmq消息队列
				sendMessage(item.getId(), "update");
				flag=true;
				//调用前台接口
				String uri="http://www.taotao.com/cache/item/"+item.getId()+".html";
				try {
					//调用httpClient发起请求
					apiService.doPost(uri,null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
