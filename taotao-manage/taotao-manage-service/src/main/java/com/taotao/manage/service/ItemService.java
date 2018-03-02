package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
@Service
public class ItemService extends BaseService<Item> {
	@Autowired
	private ItemDescService itemDescService;
	/**
	 * 新增商品和商品描述
	 * @param item
	 * @param desc
	 */
	public boolean save(Item item, String desc) {
		boolean flag=false;
		//设置item id为null,数据库主键自增
		item.setId(null);
		//设置商品状态，1-正常，2-下架，3-删除,
		item.setStatus(1);
		//新增商品
		Integer result = super.save(item);
		if(result==1){
			//创建商品描述对象
			ItemDesc itemDesc = new ItemDesc();
			//设置商品id
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			//新增商品描述对象
			Integer update = itemDescService.save(itemDesc);
			if(update==1){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * 分页查询并排序
	 */
	public PageInfo<Item> queryPageListAndSort(Integer page, Integer rows,
			String orderByClause) {
		return	super.queryPageListAndSort(page, rows, orderByClause);
	}

	/**
	 * 编辑Item与ItemDesc
	 * @param item
	 * @param desc
	 * @return
	 */
	public boolean updateItemAndItemDesc(Item item, String desc) {
		boolean flag=false;
		item.setStatus(null);
		Integer updateItem = super.updateSelective(item);
		if(updateItem==1){
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			Integer updateItemDesc = itemDescService.updateSelective(itemDesc);
			if(updateItemDesc==1){
				flag=true;
			}
		}
		return flag;
	}
}
