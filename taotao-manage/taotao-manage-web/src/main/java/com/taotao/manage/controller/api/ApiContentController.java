package com.taotao.manage.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import com.taotao.manage.vo.DataGridResult;

@Controller
@RequestMapping("api/content")
public class ApiContentController {
	@Autowired
	private ContentService contentService;
	/**
	 * 根据类目id查询content
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<Content>> queryContent(
			@RequestParam(value="categoryId",defaultValue="0")Long categoryId){
		try {
			Content record = new Content();
			record.setCategoryId(categoryId);
			List<Content> list= contentService.queryListByRecord(record);
			if(list!=null){
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
