package com.taotao.cart.utils;

import com.taotao.cart.pojo.User;



public abstract class UserThreadLocal {

	private static final ThreadLocal<User> TL=new ThreadLocal<>();
	
	public static void set(User user){
		TL.set(user);
	}
	
	public static User get(){
		return TL.get();
	}
	
	public static void remove(){
		TL.remove();
	}
}
