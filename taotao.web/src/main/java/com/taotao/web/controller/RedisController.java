package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;
@RequestMapping("cache/item/")
@Controller
public class RedisController {
	@Autowired
	private RedisService redisService;
	@Autowired
	private ItemService itemService;
	
	@PostMapping("{itemId}")
	public ResponseEntity<Void> deleteCache(@PathVariable("itemId")Long itemId){
		try {
			//删除redis中的缓存
			redisService.del(itemService.REDIS_ITEM_KEY+itemId);
			System.out.println("======================");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
}
