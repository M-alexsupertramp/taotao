package cn.itcast.springboot.test;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.itcast.springboot.config.SpringConfig;
import cn.itcast.springboot.pojo.User;
import cn.itcast.springboot.service.UserService;

public class MainTest {
	public static void main(String[] args) {
		//通过Java配置来是咧话Spring容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		//在Spring容器中获取bean对象
		UserService userService = context.getBean(UserService.class);
		
		//调用对象中的方法
		List<User> userList = userService.queryUserList();
		
		for (User user : userList) {
			System.out.println(user);
		}
		
		//销毁容器
		context.destroy();
	}
}
