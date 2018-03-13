package com.taotao.web.service;


import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.taotao.common.service.ApiService;
import com.taotao.web.pojo.Cart;
import com.taotao.web.pojo.User;
import com.taotao.web.utils.UserThreadLocal;

@Service
public class CartService {

	@Autowired
	private ApiService apiService;

	public List<Cart> queryCartList() {
		User user = UserThreadLocal.get();
		String url="http://cart.taotao.com/api/cart/list/"+user.getId()+".html";
		try {
			//调用cart.taotao.com系统接口获取数据
			String jsonNode = apiService.doGet(url,null);
			if(StringUtils.isNotEmpty(jsonNode)){
				return PropertiesService.MAPPER.readValue(jsonNode,PropertiesService.MAPPER.getTypeFactory()
								.constructCollectionType(List.class, Cart.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
