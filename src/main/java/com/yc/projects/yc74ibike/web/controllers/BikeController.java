package com.yc.projects.yc74ibike.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.yc74ibike.bean.Bike;
import com.yc.projects.yc74ibike.service.BikeService;
import com.yc.projects.yc74ibike.web.model.JsonModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

// @RestController   ->   @Controller  +   @ResponseBody
@Controller
@Api(value="小辰出行单车信息操作接口",tags= {"单车信息","控制层"})
public class BikeController {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private BikeService bikeService;
	
	/**
	 * 扫码开锁
	 * @param jsonModel:返回值部分，
	 * @param bike:     必须的参数:  bid,经度,纬度
	 * @return
	 */
	@RequestMapping(value="/open",method= {RequestMethod.POST})
	@ApiOperation(  value="用户端开锁操作",notes="给指定的共享单车开锁，参数以json格式传过来")
	public @ResponseBody JsonModel open( @ApiIgnore JsonModel jsonModel,  @RequestBody Bike bike    ) {
		logger.info("请求参数:"+bike);
		try {
			bikeService.open(   bike );
			jsonModel.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.setCode(0);
			jsonModel.setMsg(    e.getMessage() );
		}
		return jsonModel;
	}
	
	

}
