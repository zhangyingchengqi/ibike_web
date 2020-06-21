package com.yc.projects.yc74ibike.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 这个类对应了mongo中的一个文档  ,多个文档形成一个collection
 * 
 *  database  ->  collections(表) ->  document(记录)

 */
@Document(collection="users")
public class User implements Serializable{

	private static final long serialVersionUID = 4972669035852334271L;

	private int status;   //用户的状态:  0 刚注册   1 押金缴纳   2. 实名认证
	//这个字段创建索引
	//@Indexed(unique = true)
	private String phoneNum;
	private String name;   //用户名
	private String idNum;   //身份证
	private double deposit;  //押金
	private double balance;  //余额。

	//这个属性在数据库中不存储
	@Transient     // 瞬态化
	private String verifyCode;

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "User [status=" + status + ", phoneNum=" + phoneNum + ", name=" + name + ", idNum=" + idNum + ", deposit=" + deposit + ", balance=" + balance + ", verifyCode=" + verifyCode + "]";
	}

	
	

}
