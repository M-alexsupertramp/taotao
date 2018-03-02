package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 通过parentId查询商品类目--同步的方式
	 * 携带参数id--通过浏览器调试获得
	 * 要返回的数据类型是json数组,对应List<ItemCat>
	 * 必须的几个字段：
		id：唯一标示
		text：显示名称
		state：是否是父节点
	 * @param parentId
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ItemCat>> queryItemCat(@RequestParam(value="id",defaultValue="0")Long parentId){
		try {
			ItemCat record = new ItemCat();
			record.setParentId(parentId);
			record.setStatus(1);
			List<ItemCat> list=itemCatService.queryListByRecord(record);
			if(CollectionUtils.isEmpty(list)){
				//资源没找到返回404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			//找到资源返回200
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			//抛出异常,返回500
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	@GetMapping(value="{id}")
	@ResponseBody
	public ResponseEntity<ItemCat> queryItemDesc(@PathVariable("id")Long id){
		try {
			ItemCat itemCat = itemCatService.queryById(id);
			if(itemCat==null){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(itemCat);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
}
