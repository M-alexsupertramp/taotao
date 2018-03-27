package com.taotao.cart.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;

@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ItemService itemService;
	public void addItemToCart(Long itemId, Long userId) {
		//查询当前商品是否已经在购物车
		Cart record = new Cart();
		record.setUserId(userId);
		record.setItemId(itemId);
		Cart cart = cartMapper.selectOne(record);
		if(cart==null){
			//不存在,直接新增一个
			cart=new Cart();
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cart.setItemId(itemId);
			cart.setUserId(userId);
			//todo 新增的商品数量应该由页面传递,现在是默认为1
			cart.setNum(1);
			
			//查询商品数据,写入购物车
			Item item=itemService.queryItemById(itemId);
			//todo 健壮性判断
			cart.setItemImage(item.getImages()[0]);
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			
			cartMapper.insert(cart);
		}else{
			//已经存在,直接+1
			//todo,新增的商品数量应该由页面传递,现在默认为1
			cart.setNum(cart.getNum()+1);
			cart.setUpdated(new Date());
			cartMapper.updateByPrimaryKey(cart);
		}
	}
	
	/**
	 * 查询购物车列表
	 * @param userId
	 * @return
	 */
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
