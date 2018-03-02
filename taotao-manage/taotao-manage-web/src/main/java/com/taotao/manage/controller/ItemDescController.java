package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
@Controller
@RequestMapping("item/desc")
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	
	
	/**
	 * GET方式的请求,异步
	 * 请求参数id
	 * 返回数据ItemDesc的json格式对象
	 * @param id
	 * @return
	 */
	@GetMapping(value="{id}")
	@ResponseBody
	public ResponseEntity<ItemDesc> queryItemDesc(@PathVariable("id")Long id){
		try {
			ItemDesc itemDesc = itemDescService.queryById(id);
			if(itemDesc==null){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
