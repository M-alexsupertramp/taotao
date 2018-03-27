package com.taotao.cart.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.common.utils.CookieUtils;
/**
 * 未登录时,数据保存到cookie中
 * @author Mary
 *
 */
@Service
public class CookieCartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ItemService itemService;
	
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	private static final String COOKIE_NAME="TT_CART";
	
	//添加商品
	public void addItemToCart(Long itemId,HttpServletRequest request,HttpServletResponse response) {
		//获取cookie中的购物车
		Map<Long,Cart> carts=getCookieCarts(request);
		
		//获取购物车中该itemId的信息
		Cart cart = carts.get(itemId);
		
		if(cart==null){
			//不存在直接添加一个
			cart=new Cart();
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cart.setItemId(itemId);
			 // TODO 新增的商品数量应该由页面传递，现在是默认为1
            cart.setNum(1);

            //查询商品数据,写入购物车
            Item item = itemService.queryItemById(itemId);
            
            //健壮性判断
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
		}else{
			//已经存在,直接num+1
			// TODO 新增的商品数量应该由页面传递，现在是默认为1
			cart.setNum(cart.getNum()+1);
			cart.setUpdated(new Date());
		}
		carts.put(itemId,cart);
		setCookieCarts(request,response,carts);
	}
	
	/**
	 * 将购物车写入cookie
	 * @param request
	 * @param response
	 * @param carts
	 */
	private void setCookieCarts(HttpServletRequest request,
			HttpServletResponse response, Map<Long, Cart> carts) {
		
		try {
			//设置Cookie的值 在指定时间内生效, 编码参数
			//把购物车写入cookie,设置cookie生命尽可能长,并且数据中有中文,一定要编码
			 CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts),Integer.MAX_VALUE,true);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取coolie中购物车的方法
	 * @param request
	 * @return
	 */
	private Map<Long, Cart> getCookieCarts(HttpServletRequest request) {
		//先尝试获取cookie中的数据
		String jsonData = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
		Map<Long,Cart> carts=null;
		try {
			/**
		     * Method for constructing a {@link MapType} instance
		     *<p>
		     * NOTE: type modifiers are NOT called on constructed type itself; but are called
		     * for contained types.
		     */
			//将数据反序列化
			carts= MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructMapType(Map.class, Long.class, Cart.class));
			
		}  catch (Exception e) {
			e.printStackTrace();
			carts=new HashMap<>();
		}
		return carts;
	}


	public List<Cart> queryCartList(Long userId) {
		//准备查询条件
		Example example = new Example(Cart.class);
		//查询条件
		example.createCriteria().andEqualTo("userId", userId);
		//根据创建时间排序
		example.setOrderByClause("created DESC");
		
		//todo 分页查询自己实现
		return cartMapper.selectByExample(example);
	}
	
	/**
	 * 修改商品数量
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @param num
	 */
	public void updateNum(Long userId, Long itemId, Integer num) {
		Cart record = new Cart();
		record.setNum(num);
		
		//修改条件
		Example example = new Example(Cart.class);
		example.createCriteria().andEqualTo("userId", userId).andEqualTo("itemId", itemId);
		
		cartMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 根据商品id和用户id,删除购物车中的商品
	 * @param userId
	 * @param itemId
	 */
	public void deleteItemFromCart(Long userId, Long itemId) {
			Cart record = new Cart();
			record.setItemId(itemId);
			record.setUserId(userId);
			cartMapper.delete(record);
	}
}
