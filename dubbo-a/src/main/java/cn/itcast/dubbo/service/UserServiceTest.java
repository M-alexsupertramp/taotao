package cn.itcast.dubbo.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.dubbo.pojo.User;

public class UserServiceTest {

	private UserService userService;

	@Before
	public void setUp() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:dubbo/*.xml");
		this.userService = applicationContext.getBean(UserService.class);
	}

	@Test
	public void testQueryAll() {
		List<User> users = this.userService.queryAll();
		for (User user : users) {
			System.out.println(user);
		}
	}

}
