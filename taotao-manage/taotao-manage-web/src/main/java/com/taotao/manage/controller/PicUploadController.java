package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.manage.vo.PicUploadResult;

@Controller
@RequestMapping("pic/upload")
public class PicUploadController {

	private static final List<String> EXTENTIONS = Arrays.asList(".jpg",".png",".bmp",".jpeg");
	
	/**
	 * 上传文件
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public PicUploadResult upload(@RequestParam("uploadFile")MultipartFile file,HttpServletRequest request){
			//最终返回的结果
			PicUploadResult result = new PicUploadResult();
			//把结果初始化为上传失败
			result.setError(1);
			try {
			//做数据校验
			//1.校验后缀名
			//获取文件的后缀名
			String filename = file.getOriginalFilename();
			String extension="."+StringUtils.substringAfterLast(filename, ".");
			if(!EXTENTIONS.contains(extension)){
				return result;
			}
			//2.校验文件的内容
			BufferedImage image = ImageIO.read(file.getInputStream());
			if(image==null){
				return result;
			}
			//将文件保存在本地的upload下
			file.transferTo(new File(request.getSession().getServletContext().getRealPath("upload"),filename));
			//设置url到结果
			result.setUrl("http://manage.taotao.com/upload/"+filename);
			result.setError(0);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
	}
}
