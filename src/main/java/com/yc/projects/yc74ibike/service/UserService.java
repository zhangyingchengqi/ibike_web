package com.yc.projects.yc74ibike.service;

import com.yc.projects.yc74ibike.bean.User;

public interface UserService {
	/*
	 * 自动生成验证码，并发送到   指定手机  phoneNum
	 */
	public void genVerifyCode(String nationCode, String phoneNum) throws Exception; 

	public boolean verify(User user) ;
	
	/**
	 * 押金充值
	 * @param user
	 * @return
	 */
	public boolean deposit(User user);
	
	/**完成身份验证 */
	public boolean identity(User user);

}
