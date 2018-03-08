package com.taotao.sso.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import com.taotao.sso.vo.TaotaoResult;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String COOKIE_NAME = "TT_TOKEN";

	/**
	 * 跳转到注册页面
	 * @return
	 */
	@GetMapping("register")
	public String toRegister(){
		return "register";
	}
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@GetMapping("login")
	public String toLogin(){
		return "login";
	}
	
	
	/**
	 * 校验数据是否有效
	 * @param param 要校验的数据
	 * @param type 数据类型
	 * @return true数据合格, false数据不合格
	 */
	@GetMapping("check/{param}/{type}")
	public ResponseEntity<Boolean> checkUserData(@PathVariable("param")String param,
			@PathVariable("type")Integer type){
		try {
			Boolean boo=userService.queryData(param,type);
			if(boo==null){
				//没有结果,参数有误,返回400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			return ResponseEntity.ok(boo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 返回值:json数据,status和msg属性
	 * @param user  @Valid开启对user对象的校验
	 * @param BindingResult result 如果有异常字段,会把异常信息封装到这个对象中
	 * @return
	 */
	@PostMapping("doRegister")
	@ResponseBody
	public TaotaoResult saveUser(@Valid User user, BindingResult result){  
		
		if(result.hasErrors()){
			//准备收集错误信息
			List<String> msgs=new ArrayList<>();
			//获取错误信息
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				msgs.add(error.getDefaultMessage());
			}
			//参数有问题,返回400
			return TaotaoResult.build(400, StringUtils.join(msgs, "|"));
		}
		
		//注册用户
		try {
			Boolean boo=userService.register(user);
			if(boo){
				//注册成功 返回200
				return TaotaoResult.ok();
			}
			return TaotaoResult.build(400,"请核对信息后重试");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return TaotaoResult.build(500,"服务器故障!");
	}
	/**
	 * 业务逻辑:如果登录成功,需要将token保存到cookie中
	 * @param User user
	 * @return json数据
	 */
	@PostMapping("doLogin")
	@ResponseBody
	public TaotaoResult doLogin( User user,HttpServletRequest request,HttpServletResponse response){  
		//用户登录
		try {
			//尝试登录并获取TOKEN值
			String token=userService.login(user);
			if(StringUtils.isNotEmpty(token)){
				//如果不为空,代表登录成功
				//1.将token存入cookie中
				CookieUtils.setCookie(request, response, COOKIE_NAME, token);
				//2.返回200状态码,业务状态码也为200
				return TaotaoResult.ok();
			}
			//没有token,代表用户名或密码错误,返回业务代码400
			return TaotaoResult.build(400,"用户名或密码错误!");
		} catch (Exception e) {
			e.printStackTrace();
			//出现异常,不能返回,需要处理异常,并记录参数.
			logger.error("登录失败,username:"+user.getUsername()+",password:"+user.getPassword()+e);
		}
		return TaotaoResult.build(500,"服务器故障!");
	}
	
	
	/**
	 * 校验数据是否有效
	 * @param param 要校验的数据
	 * @param type 数据类型
	 * @return true数据合格, false数据不合格
	 */
	@GetMapping("{token}")
	@ResponseBody
	public ResponseEntity<User> checkLoginToken(@PathVariable("token")String token){
		try {
			//获取用户的登录信息
			User user=userService.queryByToken(token);
			if(user==null){
				//登录超时,返回404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
