package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 通过HttpClient进行远程调用的Service
 * @author Mary
 *
 */
@Service
public class ApiService {

	    @Autowired(required=false) //按需注入
	    private CloseableHttpClient httpClient;

	    // 默认的响应处理器
	    private ResponseHandler<String> defaultHandler = new BasicResponseHandler();

	    
	    /**
	     * 发起无参的GET请求
	     * 
	     * @param uri
	     * @return
	     */
	    public String doGet(String uri) {
	        return execute(new HttpGet(uri));
	    }

	    /**
	     * 发起有参的GET请求
	     * 
	     * @param uri
	     * @return
	     */
	    public String doGet(String uri, Map<String, String> params) {
	        try {
	            URIBuilder uriBuilder = new URIBuilder(uri);
	            if (params != null) {
	                for (Map.Entry<String, String> entry : params.entrySet()) {
	                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
	                }
	            }
	            return execute(new HttpGet(uriBuilder.build().toString()));
	        } catch (URISyntaxException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 无参的POST请求
	     * 
	     * @param uri
	     * @return
	     */
	    public String doPost(String uri) {
	        return execute(new HttpPost(uri));
	    }

	    /**
	     * 有参的POST请求
	     * 
	     * @param uri
	     * @return
	     */
	    public String doPost(String uri, Map<String, String> params) {
	        try {
	            HttpPost httpPost = new HttpPost(uri);
	            if (params != null) {
	                List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
	                for (Map.Entry<String, String> entry : params.entrySet()) {
	                    parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	                }
	                // 构造一个form表单式的实体
	                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
	                // 将请求实体设置到httpPost对象中
	                httpPost.setEntity(formEntity);
	            }
	            return execute(httpPost);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     *有参的POST请求,提交JSON数据
	     */
	    @SuppressWarnings("all")
		public <T> T doPostJson(String uri,String jsonData, ResponseHandler<T> handler) throws ClientProtocolException, IOException{
	    	//判断是否有响应处理,如果没有,使用默认的
	    	handler= handler==null? (ResponseHandler<T>) defaultHandler :handler;
	    	//创建http POST请求
	    	HttpPost httpPost = new HttpPost(uri);
	    	if(StringUtils.isNoneEmpty(jsonData)){
	    		//构造一个json格式的实体
	    		StringEntity formEntity = new StringEntity(jsonData,ContentType.APPLICATION_JSON);
	    		//将请求实体设置到httpPost对象中
	    		httpPost.setEntity(formEntity);
	    	}
	    	return httpClient.execute(httpPost,handler);
	    }
	    
	    public String execute(HttpUriRequest request) {
	        try {
	            return this.httpClient.execute(request, new BasicResponseHandler());
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}

	
