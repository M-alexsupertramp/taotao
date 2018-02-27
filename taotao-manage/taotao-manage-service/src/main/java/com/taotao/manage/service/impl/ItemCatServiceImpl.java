package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Override
	public List<ItemCat> queryItemCat(Long pid) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(pid);
		return itemCatMapper.select(itemCat);
	}

}
