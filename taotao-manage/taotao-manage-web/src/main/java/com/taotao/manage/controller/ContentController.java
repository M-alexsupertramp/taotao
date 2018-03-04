package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import com.taotao.manage.vo.DataGridResult;

@Controller
@RequestMapping("content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	
	@PostMapping
	public ResponseEntity<Void> insertContent(Content content){
		try {
			contentService.save(content);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * 根据类目id查询content
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<DataGridResult<Content>> queryContent(
			@RequestParam(value="categoryId",defaultValue="0")Long categoryId,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="30")Integer rows){
		try {
			Content record = new Content();
			record.setCategoryId(categoryId);
			PageInfo<Content> pageInfo = contentService.queryPageListByWhere(record, page, rows);
			if(pageInfo!=null){
				return ResponseEntity.ok(new DataGridResult<>(pageInfo.getTotal(),pageInfo.getList()));
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
