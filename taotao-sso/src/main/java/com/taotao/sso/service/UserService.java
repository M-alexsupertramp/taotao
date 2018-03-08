package com.taotao.sso.service;


import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER=new ObjectMapper();
	

	public Boolean queryData(String param, Integer type) {
		
		User record = new User();
		switch (type){
		case 1:
			record.setUsername(param);
			break;
		case 2:
			record.setPhone(param);
			break;
		case 3:
			record.setEmail(param);
			break;
		default:
			return null;
		}
		return userMapper.selectCount(record)==0;
	}
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public Boolean register(User user) {
		user.setId(null);
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//对密码进行加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		//添加用户
		int count=userMapper.insert(user);
		return count==1;
	}
	
	/**
	 * 登录功能,登录成功,保存token,并将登录信息保存到redis中
	 * @param user
	 * @return
	 * @throws JsonProcessingException
	 */
	public String login(User user) throws JsonProcessingException {
		User record = new User();
		record.setUsername(user.getUsername());
		//查找用户
		User queryUser = userMapper.selectOne(record);
		//如果没有用户,直接返回null
		if(queryUser==null){
			return null;
		}
		//判断密码是否正确
		if(!StringUtils.equals(queryUser.getPassword(), DigestUtils.md5Hex(user.getPassword()))){
			//密码不正确
			return null;
		}
		//登录成功,做2监视器
		//1.生成token并返回,为了保证安全,再次用md5进行加密
		String token = DigestUtils.md5Hex(user.getUsername()+System.currentTimeMillis());
		//2.把用户信息保存到redis中,这里key直接用token,但是为了和其他的key进行区分,加一个前缀TOKEN_TODO 将key的前缀和缓存时间抽取到配置文件中
		redisService.set("TOKEN_"+token, MAPPER.writeValueAsString(queryUser) , 1800);
		return token;
	}
	
	
	//从缓存查询到用户信息后,一定要重置缓存的存活时间!代表用户一直处于活跃状态!
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	public User queryByToken(String token) {
		String key="TOKEN_"+token;
		String jsonData = redisService.get(key);
		
		if(StringUtils.isEmpty(jsonData)){
			return null;
		}
		try {
			//刷新缓存中的存活时间
			redisService.expire(key, 1800);
			return MAPPER.readValue(jsonData, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
