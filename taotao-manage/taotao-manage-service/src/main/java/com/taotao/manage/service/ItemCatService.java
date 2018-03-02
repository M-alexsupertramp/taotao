package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taotao.common.vo.ItemCatData;
import com.taotao.common.vo.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {
	
	/**
     * 查询全部商品类目，并且封装为树结构的VO对象返回
     */
	public ItemCatResult queryAllToTree() {
		//查询出全部结果
		List<ItemCat> list=super.queryAll();
		//转为map存储,key为父节点ID,value为父节点下所有子节点数据集合.
		Map<Long,List<ItemCat>> itemCatMap=new HashMap<Long,List<ItemCat>>();
		for (ItemCat itemCat : list) {
			if(!itemCatMap.containsKey(itemCat.getParentId())){
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);
		}
		//创建结果对象
		ItemCatResult result = new ItemCatResult();
		//获取1级类目
		List<ItemCat> level1List = itemCatMap.get(0L);
		for (ItemCat itemCat : level1List) {
			//创建1级数据对象
			ItemCatData data = new ItemCatData();
			//将1级数据对象添加到1级类目集合
			result.getItemCats().add(data);
			data.setUrl("/products/"+itemCat.getId()+".html");
			data.setName("<a href='" + data.getUrl() + "'>" + itemCat.getName() + "</a>");
			//创建2级目录数据集合
			ArrayList<Object> level2DataList = new ArrayList<>();
			data.setItems(level2DataList);
			//获取2级类目数据
			List<ItemCat> level2List = itemCatMap.get(itemCat.getId());
			for (ItemCat itemCat2 : level2List) {
				//创建2级数据对象
				ItemCatData data2 = new ItemCatData();
				//将2级数据对象添加到2级类目集合
				level2DataList.add(data2);
				data2.setUrl("/products/"+itemCat2.getId()+".html");
				data2.setName(itemCat2.getName());
				
				//创建3级类目数据集合
				List<String> level3DataList=new ArrayList<String>();
				data2.setItems(level3DataList);
				//查询3级目录
				List<ItemCat> level3List = itemCatMap.get(itemCat2.getId());
				for (ItemCat itemCat3 : level3List) {
					//封装3级类目数据集合
					level3DataList.add("/products/"+itemCat3.getId()+".html"+"|"+itemCat3.getName());
				}
				
			}
			if(level1List.size()>13){
				break;
			}
			
		}
		return result;
	}
	/*@Autowired
	private ItemCatMapper itemCatMappper;
	
	@Override
	public Mapper<ItemCat> getMapper() {
		return itemCatMappper;
	}*/


}
