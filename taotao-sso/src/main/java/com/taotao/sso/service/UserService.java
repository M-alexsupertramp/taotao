package com.taotao.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.sso.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
}
