package com.taotao.cart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.cart.pojo.User;
import com.taotao.common.service.ApiService;

@Service
public class UserService {

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private PropertiesService propertiesService;
	
	public User queryUserByToken(String token) {
		//通过httpClient访问SSO,查询用户信息
		try {
			String uri=propertiesService.SSO_TAOTAO_BASE_URL+"/user/"+token+".html";
			String jsonData = apiService.doGet(uri,null);//,new SimpleResponseHandler<>(User.class)
			//反序列化
			return PropertiesService.MAPPER.readValue(jsonData,User.class);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
