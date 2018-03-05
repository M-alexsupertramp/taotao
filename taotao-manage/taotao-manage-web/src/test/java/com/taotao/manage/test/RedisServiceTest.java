package com.taotao.manage.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taotao.manage.service.RedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext*.xml")
public class RedisServiceTest {

	@Autowired
	private RedisService redisService;
	
	@Test
	public void testSetStringString(){
		redisService.set("name", "mary");
	}
	@Test
	public void testSetGet(){
		String name = redisService.get("name");
		System.out.println("name==============================="+name);
	}
	
	
}
