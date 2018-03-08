package com.taotao.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 常用的常量
 * @author Mary
 *
 */
@Service
public class PropertiesService {

	@Value("${TAOTAO_MANAGE_BASE_URL}")
	public String TAOTAO_MANAGE_BASE_URL;
	
	@Value("${INDEX_AD_BASE_PATH}")
	public String INDEX_AD_BASE_PATH;
	//大广告访问路径
	@Value("${INDEX_AD1_ID}")
	public String INDEX_AD1_ID; 
	//右上角广告访问路径
	@Value("${INDEX_AD2_ID}")
	public String INDEX_AD2_ID; 
	
	//从配置文件中读取后台服务器的地址---管理系统的url
	@Value("${TAOTAO_MANAGE_BASE_URL}")
	public String MANAGE_TAOTAO_BASE_URL;
	
	@Value("${ITEM_PATH}")
	public String ITEM_PATH;
	
	
	@Value("${SSO_TAOTAO_BASE_URL}")
	public String SSO_TAOTAO_BASE_URL;
	
	@Value("${LOGIN_URL}")
	public  String LOGIN_URL;
	
	@Value("${ORDER_TAOTAO_BASE_URL}")
	public  String ORDER_TAOTAO_BASE_URL;
	
	@Value("${ORDER_CREATE}")
	public  String ORDER_CREATE;
	
	@Value("${ORDER_QUERY_BY_ID}")
	public  String ORDER_QUERY_BY_ID;

	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	}

	
