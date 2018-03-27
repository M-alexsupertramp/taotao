package com.taotao.cart.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
/**
 * 远程访问接口
 * @author Mary
 *
 */
@Controller
@RequestMapping("api/cart")
public class ApiCartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 请求路径
	 * http://cart.taotao.com/api/cart/list/"+user.getId()
	 * 对外提供查询购物车列表的功能
	 * @return
	 */
	@GetMapping("list/{userId}")
	public ResponseEntity<List<Cart>> updateItemNum(@PathVariable("userId")Long userId){
		try {
			//准备购物车数据
			List<Cart> carts = cartService.queryCartList(userId);
			if(CollectionUtils.isEmpty(carts)){
				//没数据,返回404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(carts);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
