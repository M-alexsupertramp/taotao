package com.taotao.web.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
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

	    @Autowired
	    private CloseableHttpClient httpClient;

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

	    public String execute(HttpUriRequest request) {
	        try {
	            return this.httpClient.execute(request, new BasicResponseHandler());
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}

	
