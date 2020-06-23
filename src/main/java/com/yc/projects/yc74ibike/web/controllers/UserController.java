package com.yc.projects.yc74ibike.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.yc74ibike.bean.User;
import com.yc.projects.yc74ibike.service.UserService;
import com.yc.projects.yc74ibike.web.model.JsonModel;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/recharge")
	public @ResponseBody JsonModel recharge(JsonModel jm,double balance,String phoneNum) {
		boolean b=false;
		try {
			b=userService.recharge(balance,phoneNum);
			if( b ) {
				jm.setCode(1);
			}else {
				jm.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(   e.getMessage() );
		}
		return jm;
	}
	
	
	/**
	 * 
	 * @param jm
	 * @param user:   idNum,  name,   phoneNum
	 * @return
	 */
	@PostMapping("/identity")
	public @ResponseBody JsonModel identity(JsonModel jm, User user) {
		boolean result=userService.identity(user);
		if( result) {
			jm.setCode(1);
		}else {
			jm.setCode( 0 );
		}
		return jm;
	}
	
	
	@PostMapping("/deposit")
	public @ResponseBody JsonModel deposit(JsonModel jm,User user) {
		boolean flag=userService.deposit(user);
		if( flag) {
			jm.setCode(1);
		}else {
			jm.setCode(0);
		}
		return jm;
	}

	@PostMapping("/genCode")
	public @ResponseBody JsonModel genSMSCode(JsonModel jm, String nationCode, String phoneNum) {
		String msg = "true";
		try {
			// 生成4位随机数 -> 调用短信接口发送验证码 -> 将手机号对应的验证码保存到redis中，并且设置这个key的有效时长
			userService.genVerifyCode(nationCode, phoneNum);
			jm.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg(e.getMessage());
		}
		return jm;
	}

	@PostMapping("/verify")
	public @ResponseBody JsonModel verify(JsonModel jm, User user) {
		boolean flag = false;
		try {
			flag = userService.verify(user);
			if (flag) {
				jm.setCode(1);
			} else {
				jm.setCode(0);
				jm.setMsg("校验码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setMsg("错误原因:" + e.getMessage());
		}
		return jm;
	}
}
