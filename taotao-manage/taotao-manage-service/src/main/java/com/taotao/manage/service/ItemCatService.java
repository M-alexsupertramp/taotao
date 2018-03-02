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
     * 
     * @return
     */
    public ItemCatResult queryAllToTree() {
        // 查询出全部结果
        List<ItemCat> list = super.queryAll();
        // 转为map存储，key为父节点ID，value为该父节点下所有子节点数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : list) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 创建结果对象
        ItemCatResult result = new ItemCatResult();
        // 获取1级类目
        List<ItemCat> level1List = itemCatMap.get(0L);
        for (ItemCat itemCat : level1List) {
            // 创建1级数据对象
            ItemCatData data = new ItemCatData();
            // 将1级数据对象添加到1级类目集合
            result.getItemCats().add(data);
            data.setUrl("/products/" + itemCat.getId() + ".html");
            data.setName("<a href='" + data.getUrl() + "'>" + itemCat.getName() + "</a>");
            // 如果没有子节点，跳过
            if(!itemCat.getIsParent()){
                continue;
            }
            
            // 创建2级类目数据集合
            List<ItemCatData> level2DataList = new ArrayList<>();
            data.setItems(level2DataList);
            // 获取2级类目数据
            List<ItemCat> level2List = itemCatMap.get(itemCat.getId());
            for (ItemCat itemCat2 : level2List) {
                // 创建2级数据对象
                ItemCatData data2 = new ItemCatData();
                // 将2级数据对象添加到2级类目集合
                level2DataList.add(data2);
                data2.setUrl("/products/" + itemCat2.getId() + ".html");
                data2.setName(itemCat2.getName());
                // 如果没有子节点，跳过
                if(!itemCat2.getIsParent()){
                    continue;
                }

                // 创建3级类目数据集合
                List<String> leve13DataList = new ArrayList<>();
                data2.setItems(leve13DataList);
                // 查询3级类目
                List<ItemCat> level3List = itemCatMap.get(itemCat2.getId());
                for (ItemCat itemCat3 : level3List) {
                    // 封装3级类目数据到集合
                    leve13DataList.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                }
            }
			// 前台只需要前面14条1级类目的数据
            if (result.getItemCats().size() >= 14) {
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
