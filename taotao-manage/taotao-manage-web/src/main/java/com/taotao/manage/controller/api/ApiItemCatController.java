package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.vo.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<ItemCatResult> queryItemCat(){
		try {
			ItemCatResult result=itemCatService.queryAllToTree();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
		}
	}
}
