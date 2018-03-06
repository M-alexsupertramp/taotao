package com.taotao.web.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.pojo.Item;
import com.taotao.web.service.ItemService;
import com.taotao.web.vo.ItemVo;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("{itemId}")
	public ModelAndView queryItemById(
			@PathVariable("itemId")Long itemId){
		//创建ModelAndView,并设置视图名
		ModelAndView mv = new ModelAndView("item");
		//添加模型数据
		ItemVo item = itemService.queryById(itemId);
		mv.addObject("item", item);
		return mv;
	}
}
