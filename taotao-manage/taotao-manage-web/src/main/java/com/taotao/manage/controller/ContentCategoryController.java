package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 异步请求
	 * 参数:父类目id
	 * 返回的数据:List ContentCategory 
	 * @param parentId
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ContentCategory>> queryContentCategory(
			@RequestParam(value="id",defaultValue="0")Long parentId){
		
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setParentId(parentId);
			List<ContentCategory> ContentCategorys=contentCategoryService.queryListByRecord(contentCategory);
			if(ContentCategorys!=null){
				return ResponseEntity.ok(ContentCategorys);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * 添加类目
	 * @param contentCategory
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ContentCategory> insertContentCategory(
			ContentCategory contentCategory){
		//设置初始参数
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		
		//查询父节点对象,isParent为false时,需要将字段改为true   save和update方法全部放到service中去执行
		ContentCategory parent = contentCategoryService.queryById(contentCategory.getParentId());
		try {
			boolean result = contentCategoryService.saveAndUpdateContentCategoryAndContent(contentCategory,parent);
			if(result){
				return ResponseEntity.ok(contentCategory);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * 修改名称
	 * @param id
	 * @param name
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Void> updateContentCategory(
			@RequestParam("id")Long id, @RequestParam("name")String name){
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setId(id);
			contentCategory.setName(name);
			//选择性更新
			Integer update = contentCategoryService.updateSelective(contentCategory);
			if(update==1){
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * 删除分类节点
	 * 逻辑:
	 * 1.删除当前节点
	 * 2.删除当前节点的所有子节点
	 * 3.判断当前节点的父节点是否还有其他子节点
	 * @param id
	 * @param name
	 * @return
	 */
	@DeleteMapping
	public ResponseEntity<Void> deleteContentCategoryById(
			@RequestParam("id")Long id, @RequestParam("parentId")Long parentId){
		try {
			Boolean result = contentCategoryService.deleteContentCategory(id,parentId);
			if(result){
				return ResponseEntity.ok().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
