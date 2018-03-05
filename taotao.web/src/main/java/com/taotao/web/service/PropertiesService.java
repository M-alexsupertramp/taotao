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
	
	@Value("${INDEX_AD1_ID}")
	public String INDEX_AD1_ID; 
	
	@Value("${INDEX_AD2_ID}")
	public String INDEX_AD2_ID; 
	
	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	}

	
