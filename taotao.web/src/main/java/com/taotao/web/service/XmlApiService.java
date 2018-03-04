package com.taotao.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class XmlApiService {

	@Autowired
	private CloseableHttpClient httpClient;
	
	/**
	 * 无参的get请求
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public String doGet(String uri) throws Exception{
		//创建HttpGet请求,相当于在浏览器输入地址
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response=null;
		try {
			//执行请求,相当于在浏览器地址栏敲完地址后按回车
			response=httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200){
				//解析响应获取数据
				return EntityUtils.toString(response.getEntity(),"UTF-8");
			}
		} finally {
			if(response!=null){
				response.close();
			}
		}
		return null;
	}
	
	/**
	 * 有参的get请求
	 * @param uri
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doGet(String uri, Map<String,String> params) throws Exception{
		//创建地址构建器
		URIBuilder builder = new URIBuilder(uri);
		//拼接参数
		for(Map.Entry<String, String> me:params.entrySet()){
			builder.addParameter(me.getKey(), me.getValue());
		}
		return doGet(builder.build().toString());
		
	}
	
	
	/**
	 * 有参的post请求
	 */
	public String doPost(String uri, Map<String,String> params) throws Exception{
		//创建http post请求
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"
);
		if(params!=null){
			//设置参数
			List<NameValuePair> parameters=new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, String> me :params.entrySet()) {
				parameters.add(new BasicNameValuePair(me.getKey(), me.getValue()));
			}
			//构造一个表单的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
			//将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}
		
		CloseableHttpResponse response=null;
		
		try {
			//执行请求
			response=httpClient.execute(httpPost);
			//判断返回的状态是否为200
			if(response.getStatusLine().getStatusCode()==200){
				String content=EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(content);
			}
		} finally {
			if(response!=null){
				response.close();
			}
		}
		
		return null;
	}
	
	/**
	 * 无参的post请求
	 */
	public String doPost(String uri) throws Exception{
		return doPost(uri,null);
	}
}
