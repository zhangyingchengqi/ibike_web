package com.yc.projects.yc74ibike.service;

import com.yc.projects.yc74ibike.bean.User;

public interface UserService {
	/*
	 * 自动生成验证码，并发送到   指定手机  phoneNum
	 */
	public void genVerifyCode(String nationCode, String phoneNum) throws Exception; 

	public boolean verify(User user) ;

}
