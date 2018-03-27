package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.CartService;
import com.taotao.cart.utils.UserThreadLocal;

@Controller
@RequestMapping("cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping("{itemId}")
	public String saveCart(@PathVariable("itemId")Long itemId){
		
		//获取登录用户
		User user = UserThreadLocal.get();
		if(user==null){
			//未登录
		}else{
			//已登录
			cartService.addItemToCart(itemId,user.getId());
		}
		//重定向到查询购物车列表的controller
		return "redirect:/cart/list.html";
	}
	
	@GetMapping("list")
	public ModelAndView queryCartList(){
		ModelAndView mv = new ModelAndView("cart");
		List<Cart> list=new ArrayList<Cart>();
		//获取登录用户
		User user = UserThreadLocal.get();
		if(user==null){
			//未登录
		}else{
			//已登录
			list=cartService.queryCartList(user.getId());
		}
		mv.addObject("cartList",list);
		return mv;
	}
	/**
	 * 请求路径
	 * service/cart/update/num/562379/2
	 * @param itemId 商品id
	 * @param num 商品数量
	 * @return
	 */
	@PostMapping("/update/num/{itemId}/{num}")
	public ResponseEntity<Void> updateItemNum(@PathVariable("itemId")Long itemId,
			@PathVariable("num")Integer num){
		//获取登录用户
		User user = UserThreadLocal.get();
		if(user==null){
			//未登录
		}else{
			//已登录
			cartService.updateNum(user.getId(),itemId,num);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	/**请求路径
	 * http://cart.taotao.com/cart/delete/562379
	 * @param itemId 商品id
	 * @return
	 */
	@GetMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable("itemId")Long itemId){
		//获取登录用户
		User user = UserThreadLocal.get();
		if(user==null){
			//未登录
		}else{
			//已登录
			cartService.deleteItemFromCart(user.getId(),itemId);
		}
		//重定向到查询购物车列表的controller
		return "redirect:/cart/list.html";
	}
	
	
}
