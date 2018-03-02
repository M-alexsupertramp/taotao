package com.taotao.manage.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.manage.vo.DataGridResult;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@PostMapping
	public ResponseEntity<Void> saveItem(Item item,@RequestParam("desc")String desc){
		try {
			if(StringUtils.isBlank(item.getTitle())||
					item.getPrice()==null||
					item.getCid()==null||
					item.getNum()==null){
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(itemService.save(item,desc)){
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 分页查询
	 * 异步
	 * 参数page,rows
	 * 返回值是一个jason数据 total:xxx,list<>
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping
	public ResponseEntity<DataGridResult<Item>> pageQuery(
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="30")Integer rows){
		try {
			PageInfo<Item> pageInfo=itemService.queryPageListAndSort(page, rows,"updated DESC");		
			if(pageInfo==null){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(new DataGridResult<>(pageInfo.getTotal(),pageInfo.getList()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@PutMapping
	public ResponseEntity<Void> updateItem(Item item,@RequestParam("desc")String desc){
		try {
			if(itemService.updateItemAndItemDesc(item,desc)){
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
