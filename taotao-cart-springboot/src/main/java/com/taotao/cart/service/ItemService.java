package com.taotao.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.cart.mapper.ItemMapper;
import com.taotao.cart.pojo.Item;

@Service
public class ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	public Item queryItemById(Long itemId) {
		
		Item record=new Item();
		record.setId(itemId);
		return itemMapper.selectOne(record);
	}
}
