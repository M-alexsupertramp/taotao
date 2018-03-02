package com.taotao.common.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 商品类目的数据对象
 * @author Mary
 *
 */
public class ItemCatData {
	@JsonProperty("u")
	private String url;
	@JsonProperty("n")
	private String name;
	@JsonProperty("i")
	//商品类目的下级类目集合.不确定数据类型,所以泛型通配符
	private List<?> items;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	public ItemCatData(String url, String name, List<?> items) {
		super();
		this.url = url;
		this.name = name;
		this.items = items;
	}
	public ItemCatData() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ItemCatData [url=" + url + ", name=" + name + ", items="
				+ items + "]";
	}
	
}
