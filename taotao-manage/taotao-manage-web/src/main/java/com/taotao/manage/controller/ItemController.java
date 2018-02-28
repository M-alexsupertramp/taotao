package com.taotao.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	
	@PostMapping
	public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc")String desc){
		try {
			if(item!=null&&
				(StringUtils.isBlank(item.getTitle())
					||item.getPrice()==null
					||item.getNum()==null)
					||StringUtils.isBlank(desc)){
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			itemService.save(item,desc);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
