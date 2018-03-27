package com.taotao.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.User;
import com.taotao.cart.service.PropertiesService;
import com.taotao.cart.service.UserService;
import com.taotao.cart.utils.UserThreadLocal;
import com.taotao.common.utils.CookieUtils;
/*
 * 拦截器,对登录状态进行校验
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Autowired
	private PropertiesService propertiesService;
	
	public static final String COOKIE_NAME="TT_TOKEN";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//1.获取用户的token
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
		//无论登录与都都可以操作购物车,因此不再判断是否登录
		if(StringUtils.isBlank(token)){
			return true;
		}
		//b--有token,从sso获取用户信息
		User user=userService.queryUserByToken(token);		
		
		//用户已经登录,保存用户信息
		UserThreadLocal.set(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//在视图渲染后,将ThreadLocal中的User销毁,因为Tomcat中使用线程池,多次请求会共享线程,可能会出现问题
		UserThreadLocal.remove();
	}

}
