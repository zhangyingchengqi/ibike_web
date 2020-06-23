package com.yc.projects.yc74ibike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import org.jboss.logging.Logger;

import com.mongodb.client.result.UpdateResult;
import com.yc.projects.yc74ibike.bean.User;
import com.yc.projects.yc74ibike.service.UserService;
import com.yc.projects.yc74ibike.utils.SmsUtils;

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

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean recharge(double balance, String phoneNum) {
		if(  balance<0) {
			throw new RuntimeException("充值金额不能为负数,当前为:"+balance);
		}
		boolean flag = true;
		// 跟新用户的余额
		try {
			Query q=new Query(   Criteria.where("phoneNum").is(phoneNum)  );
			Update u=new Update().inc("balance", balance);
			mongoTemplate.updateFirst(q,u , User.class,"users");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean identity(User user) {
		// TODO:调用第三方接口验证用户身份证是否是真实的。
		int status = 3;
		UpdateResult result = mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())),
				new Update().set("status", status).set("name", user.getName()).set("idNum", user.getIdNum()), User.class);
		if (result.getModifiedCount() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deposit(User user) {
		int status = 2; // 状态要变为2
		int money = 299; // 押金数
		UpdateResult result = mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())), new Update().set("status", status).set("deposit", money), User.class);
		if (result.getModifiedCount() == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证验证码并注册用户
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public boolean verify(User user) {
		boolean flag = false;
		String phoneNum = user.getPhoneNum();
		String verifyCode = user.getVerifyCode();
		String code = stringRedisTemplate.opsForValue().get(phoneNum); // 根据电话号码到
																		// redis中查是否有有效的验证码
		System.out.println(user);
		if (verifyCode != null && verifyCode.equals(code)) {
			// 验证成功后，将用户信息保存到 mongo中
			// mongoTemplate.save(user);
			mongoTemplate.insert(user);
			flag = true;
		}
		return flag;
	}

	@Override
	public void genVerifyCode(String nationCode, String phoneNum) throws Exception {
		// 短信接口的 appkey
		// String appkey = stringRedisTemplate.opsForValue().get("appkey");
		// 生成验证码
		String code = (int) ((Math.random() * 9 + 1) * 1000) + "";
		logger.info("生成的验证码为:" + code);
		// SmsUtils.sendSms(code, new String[] {nationCode+phoneNum}); //TODO:
		// 以后发送
		// redisTemplate.
		// 将数据保存到redis中，redis的key手机号，value是验证码，有效时长120秒
		stringRedisTemplate.opsForValue().set(phoneNum, code, 120, TimeUnit.SECONDS);
	}

}
