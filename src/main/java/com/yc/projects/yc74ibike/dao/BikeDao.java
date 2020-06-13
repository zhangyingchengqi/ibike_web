package com.yc.projects.yc74ibike.dao;

import com.yc.projects.yc74ibike.bean.Bike;

/**
 * 单车的操作
* @author 张影 QQ:1069595532  WX:zycqzrx1
* @date Jun 13, 2020
 */
public interface BikeDao {
	
	/**
	 * 新增一辆新车入库,并自动生成这辆车的bid, 然后在业务层调用生成二维码的功能.
	 * @param bike
	 * @return
	 */
	public Bike addBike(   Bike bike);
	
	/**
	 * 更新操作(  对应业务中的入库，上线，解锁 )
	 */
	public void updateBike(  Bike bike );
	
	/**
	 * 根据id查车
	 */
	public Bike findBike(  Long bid);
}
