package com.taotao.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemCatResult {
	
	/**
	 *通过这个注解告诉Jackson,序列化时,以data作为这个数据的key
	 */
	@JsonProperty("data")
	//一个名为data的属性,其值是一个对象的集合
	private List<ItemCatData> itemCats=new ArrayList<ItemCatData>();

	public List<ItemCatData> getItemCats() {
		return itemCats;
	}

	public void setItemCats(List<ItemCatData> itemCats) {
		this.itemCats = itemCats;
	}
	
	
}
