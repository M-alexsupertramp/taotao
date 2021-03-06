package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用页面跳转
 * @author Mary
 *
 */
@RequestMapping("/page")
@Controller
public class PageController {
	@GetMapping(value="{pageName}")
	public String toPage(@PathVariable("pageName")String pageName){
		return pageName;
	}
}
