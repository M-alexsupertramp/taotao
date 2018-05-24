package com.taotao.cart.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@Configuration
@PropertySource(value={"classpath:jdbc.properties", "classpath:env.properties",
        "classpath:httpclient.properties"}, ignoreResourceNotFound = true)
//@ComponentScan(basePackages="com.taotao")
public class TaotaoApplication {
	
}
