package com.taotao.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.service.ApiService;
import com.taotao.common.vo.TaotaoResult;
import com.taotao.web.httpclient.SimpleResponseHandler;
import com.taotao.web.pojo.Order;
@Service
public class OrderService {

	@Autowired
	private ApiService apiService;
	@Autowired
	private PropertiesService propertiesService;
	
	
	public TaotaoResult submit(Order order) {
		try {
			//请求路径
			String uri=propertiesService.ORDER_TAOTAO_BASE_URL+propertiesService.ORDER_CREATE;
			//将order序列化
			String json=PropertiesService.MAPPER.writeValueAsString(order);
			//用httpClient发起提交请求,并获取结果,因为订单提交返回的结果本身就是TaotaoResult,这里可以直接返回
			 TaotaoResult result = apiService.doPostJson(uri, json, new SimpleResponseHandler<>(TaotaoResult.class));
			 return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(500,"订单提交失败");
	}


	public Order queryOrderById(String orderId) {
		try {
			//请求路径
			String uri=propertiesService.ORDER_TAOTAO_BASE_URL+propertiesService.ORDER_QUERY_BY_ID+orderId;
			//用httpClient发起提交请求,并获取结果,因为订单提交返回的结果本身就是TaotaoResult,这里可以直接返回
			 String jsonData = apiService.doGet(uri);
			 return PropertiesService.MAPPER.readValue(jsonData, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
