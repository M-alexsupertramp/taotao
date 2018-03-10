package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.search.pojo.Item;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.PageResult;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	private static final Integer ROWS=32;
	
	/**
	 * 搜索功能
	 * @param keyword 搜索的关键词
	 * @param page 页码
	 * @return
	 */
	@GetMapping("search")
	public ModelAndView search(@RequestParam("q")String keyword,@RequestParam(value="page",defaultValue="1")Integer page){
		//解决中文乱码
		try {
			keyword=new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView("search");
		//搜索获取分页结果
		PageResult<Item>result= searchService.search(keyword,page,ROWS);
		//添加关键字模型
		mv.addObject("query", keyword);
		//添加商品列表
		mv.addObject("itemList", result.getData());
		//添加当前页
		mv.addObject("page", page);
		
		//总页数
		int total=result.getTotal().intValue();
		//添加总页数
		mv.addObject("pages", (total + ROWS - 1) / ROWS);
		return mv;
	}
	
	
}
