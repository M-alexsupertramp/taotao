package com.taotao.manage.controller.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
/**
 * 通过ControllerAdvice注解声明这个类是一个通知类,并且可以指定要拦截的对象.常用的指定方式:
 * 	annotation:带有指定注解的类会被拦截
 * 	basePackages\value:指定要拦截的类所在的包
 * @author Mary
 *
 */
@ControllerAdvice(annotations=Controller.class)
public class JsonpResponseAdvice extends AbstractJsonpResponseBodyAdvice {
	//定义默认空参构造
	public JsonpResponseAdvice(){
		//通过父类的构造函数,传递Jsonp回调函数在参数列表中的名称
		super("callback","jsonp");
	}
}
