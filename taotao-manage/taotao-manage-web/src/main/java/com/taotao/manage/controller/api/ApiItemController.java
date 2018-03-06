package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("api/item")
public class ApiItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据id查询
	 * 参数itemId
	 * 返回值是item
	 * @return
	 */
	@GetMapping("{itemId}")
	public ResponseEntity<Item> queryItemById(
			@PathVariable("itemId")Long itemId){
		try {
			Item item = itemService.queryById(itemId);
			if(item==null){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
