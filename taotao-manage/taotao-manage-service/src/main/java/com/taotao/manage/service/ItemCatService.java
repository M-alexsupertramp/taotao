package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {
	/*@Autowired
	private ItemCatMapper itemCatMappper;
	
	@Override
	public Mapper<ItemCat> getMapper() {
		return itemCatMappper;
	}*/


}
