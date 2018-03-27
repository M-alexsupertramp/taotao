package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.taotao.manage.pojo.ContentCategory;
@Service

public class ContentCategoryService extends BaseService<ContentCategory> {
	
	//@Transactional(noRollbackFor=RuntimeException.class)  抛出unchecked异常，触发事物，noRollbackFor=RuntimeException.class,不回滚  
	public boolean saveAndUpdateContentCategoryAndContent(
			ContentCategory contentCategory, ContentCategory parent) {
		boolean flag=false;
		Integer insert = super.save(contentCategory);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			Integer update = super.update(parent);
			if(insert==1&&update==1){
				flag=true;
			}
		}
		return flag;
	}
	/**
	 * 删除分类节点
	 * 逻辑:
	 * 1.删除当前节点
	 * 2.删除当前节点的所有子节点
	 * 3.判断当前节点的父节点是否还有其他子节点
	 * @param id
	 * @param parentId
	 */
	public Boolean deleteContentCategory(Long id, Long parentId) {
		ContentCategory record = new ContentCategory();
		record.setParentId(parentId);
		record.setStatus(1);
		Integer count=super.count(record);
		boolean flag=true;
		//判断父节点下是否还有其他子节点
		if(count==1){
			//如果没有其他子节点(除自己外),更新父节点isParent字段为false
			record.setParentId(null);
			record.setStatus(null);
			record.setIsParent(false);
			record.setId(parentId);//父节点的id是子节点的父id
			//更新当前节点
			flag = super.updateById(record);
		}
		//删除当前节点的所有子节点
		if(flag){
			ContentCategory myself = super.queryById(id);
			List<Object> childrenList=new ArrayList<Object>();
			childrenList.add(myself.getId());
			if(myself.getIsParent()){
				getSubIds(childrenList, myself.getId());
			}
			Integer countStatus = super.deleteByIds("id", childrenList);
			if(countStatus==0){
				flag=false;
			}
		}
		
		return flag;
	}
		
//		//准备集合,存放要删除的所有节点ID
//		List<Object> ids=new ArrayList<>();
//		//获取要删除的所有分类及子分类
//		getSubIds(ids, id);
//		//删除这些分类
//		super.deleteByIds("id", ids);
//		
//		//判断父节点的isParent状态
//		ContentCategory record = new ContentCategory();
//		record.setParentId(parentId);
//		
//		//查询父节点下的所有子节点
//		List<ContentCategory> list=super.queryListByRecord(record);
//		
//		//如果已经没有子节点,修改父节点的isParent为false
//		if(CollectionUtils.isEmpty(list)){
//			record=new ContentCategory();
//			record.setIsParent(false);
//			record.setId(parentId);
//			//修改父节点内容
//			super.updateSelective(record);
//		}
//		//需要继续删除分类下的内容
	
	
	 private void getSubIds(List<Object> ids, Long pid) {
	        // 把当前节点添加到要删除的节点集合中
	        ids.add(pid);
	        // 查询父节点下的所有子分类
	        ContentCategory category = new ContentCategory();
	        category.setParentId(pid);
	        List<ContentCategory> list = super.queryListByRecord(category);
	        // 判断是否有子节点
	        if(!CollectionUtils.isEmpty(list)){
	            // 遍历子分类, 如果子分类下还有子节点，证明是父节点，那么遍历子节点，然后递归处理
	            for (ContentCategory contentCategory : list) {
	                getSubIds(ids,contentCategory.getId());
	            }
	        }
	 }
}
