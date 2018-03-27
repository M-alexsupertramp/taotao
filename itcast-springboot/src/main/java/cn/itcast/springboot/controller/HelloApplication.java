package cn.itcast.springboot.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  //springmvc的配置器
@SpringBootApplication(exclude={RedisAutoConfiguration.class})  //SpringBoot项目的核心注解,主要是开启自动配置
@Configuration  //spring的配置类
public class HelloApplication {
	@RequestMapping("hello")
	@ResponseBody
	public String hello(){
		return "hello world!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
		
	}
	
	
	
	
}
