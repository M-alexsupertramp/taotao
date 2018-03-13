package com.taotao.web.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.vo.TaotaoResult;
import com.taotao.web.interceptor.LoginInterceptor;
import com.taotao.web.pojo.Cart;
import com.taotao.web.pojo.Order;
import com.taotao.web.pojo.User;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.utils.UserThreadLocal;
import com.taotao.web.vo.ItemVo;

@RequestMapping("order")
@Controller
public class OrderController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;

	@GetMapping("{itemId}")
	public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("order");
		ItemVo itemVo = itemService.queryById(itemId);
		mv.addObject("item", itemVo);
		return mv;
	}

	/**
	 * 请求路径/order/submit.html
	 * 
	 * @param Order
	 * @return 包含status和data的对象, TaotaoResult 注意:在提交订单前,需要查询当前登录用户信息,添加到订单的属性中
	 */
	@PostMapping("submit")
	@ResponseBody
	public TaotaoResult submitOrder(Order order,
			@CookieValue(LoginInterceptor.COOKIE_NAME) String token) {
		// 获取当前用户信息
		// User user = userService.queryUserByToken(token);
		// 从ThreadLocal中获取当前登录用户信息
		User user = UserThreadLocal.get();
		// 设置订单的用户信息
		order.setBuyerNick(user.getUsername());
		order.setUserId(user.getId());

		// 提交订单
		TaotaoResult result = orderService.submit(order);

		return result;
	}

	@GetMapping("success")
	public ModelAndView success(@RequestParam("id") String orderId) {
		ModelAndView mv = new ModelAndView("success");
		// 封装订单信息
		Order order = orderService.queryOrderById(orderId);
		mv.addObject("order", order);

		// 封装送货日期,应该根据物流系统进行对接查询,
		mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
		return mv;
	}

	/**
	 * 请求路径/order/create.html
	 * 
	 * @param 无
	 * @return 3）	返回值：这里要跳转到订单确认页，
	 * 因此返回ModelAndView。到order-cart.jsp页面。但是要加载购物车信息。
	 */
	@GetMapping("create")
	public ModelAndView submitOrder() {
		ModelAndView mv=new ModelAndView("order-cart");
		//查询购物车
		List<Cart> carts=cartService.queryCartList();
		//添加商品信息
		mv.addObject("carts", carts);
		return mv;
	}

}
