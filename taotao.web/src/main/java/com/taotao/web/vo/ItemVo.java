package com.taotao.web.vo;

import org.apache.commons.lang3.StringUtils;

import com.taotao.web.pojo.Item;

public class ItemVo extends Item {

	
	public String[] getImages(){
		return StringUtils.split(getImage(), ",");
	}
	
}
