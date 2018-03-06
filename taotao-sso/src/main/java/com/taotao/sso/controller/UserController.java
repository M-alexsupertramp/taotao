package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

	/**
	 * 跳转到注册页面
	 * @return
	 */
	@GetMapping("register")
	public String toRegister(){
		return "register";
	}
	
	
}
