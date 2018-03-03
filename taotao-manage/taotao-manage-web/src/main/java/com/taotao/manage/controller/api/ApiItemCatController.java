package com.taotao.manage.controller.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.vo.ItemCatResult;
import com.taotao.manage.service.ItemCatService;

@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	private static ObjectMapper mapper=new ObjectMapper();
	 /**
     * 查询全部商品类目信息，并封装为tree结构
     * @return
     */
	@GetMapping
	public ResponseEntity<ItemCatResult> queryItemCat(){
		try {
			ItemCatResult result=itemCatService.queryAllToTree();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
		}
	}
	
	 /**
     * 查询全部商品类目信息，并封装为tree结构
     * @return
     */
	/*@GetMapping
	public ResponseEntity<String> queryItemCat(@RequestParam(value="callback",required=false)String callback){
		try {
			ItemCatResult result=itemCatService.queryAllToTree();
			//将结果转为JSON
			String jsonResult=mapper.writeValueAsString(result);
			//判断是否是跨域请求,如果是,则添加跨域回调函数
			if(StringUtils.isNoneEmpty(callback)){
				return ResponseEntity.ok(callback+"("+jsonResult+")");
			}
			//如果不是,直接返回结果和成功状态码
			return ResponseEntity.ok(jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
			//返回异常状态码
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
		}
	}*/
}




