package com.yc.projects.yc74ibike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import org.jboss.logging.Logger;
import com.yc.projects.yc74ibike.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	// 操作redis中的v是对象类型的数据
	@Autowired
	private RedisTemplate redisTemplate;
	// 操作redis中的字符串类型数据
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void genVerifyCode(String nationCode, String phoneNum) throws Exception {
		// 短信接口的 appkey
		// String appkey = stringRedisTemplate.opsForValue().get("appkey");
		// 生成验证码
		String code = (int) ((Math.random() * 9 + 1) * 1000) + "";
		logger.info("生成的验证码为:" + code);
		// SmsUtils.sendSms(code, new String[] {nationCode+phoneNum});    //TODO: 以后发送
		// redisTemplate.
		// 将数据保存到redis中，redis的key手机号，value是验证码，有效时长120秒  
		stringRedisTemplate.opsForValue().set(phoneNum, code, 120, TimeUnit.SECONDS);
	}

}
