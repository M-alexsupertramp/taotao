package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.IndexService;

@Controller
public class IndexController {
	
	@Autowired
	private IndexService indexService;
	
	@GetMapping("index")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView("index");
		//查询大广告数据
		String indexAd1=indexService.queryIndexAD1();
		modelAndView.addObject("indexAd1", indexAd1);
		
		//查询右上角广告数据
		String indexAd2=indexService.queryIndexAD2();
		modelAndView.addObject("indexAd2", indexAd2);
		return modelAndView;
	}
	
}
