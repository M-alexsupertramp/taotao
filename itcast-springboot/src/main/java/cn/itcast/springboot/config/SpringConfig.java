package cn.itcast.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cn.itcast.springboot.dao.UserDao;
/**
 * @ComponentScan 默认扫描@SpringBootApplication所在类的同级目录以及它的子目录。
 * @author Mary
 *
 */
@Configuration//通过该注解来表明该类是一个Spring配置类,相当于一个xml文件
@ComponentScan(basePackages="cn.itcast.springboot") //配置扫描包
@PropertySource(value={"classpath:jdbc.properties","xxx"},ignoreResourceNotFound=true)//配置多个配置文件
public class SpringConfig {
	@Value("${jdbc.url}")
	private String jdbcUrl;
	
	@Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;
   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Bean// 通过该注解来表明是一个Bean对象，相当于xml中的<bean>
	public UserDao getUserDao(){
		return new UserDao();
	}

}
