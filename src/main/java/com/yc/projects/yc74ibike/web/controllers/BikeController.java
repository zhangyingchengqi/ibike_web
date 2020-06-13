package com.yc.projects.yc74ibike.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.projects.yc74ibike.bean.Bike;
import com.yc.projects.yc74ibike.service.BikeService;
import com.yc.projects.yc74ibike.web.model.JsonModel;

import io.swagger.annotations.Api;

// @RestController   ->   @Controller  +   @ResponseBody
@Controller
@Api(value="小辰出行单车信息操作接口",tags= {"单车信息","控制层"})
public class BikeController {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private BikeService bikeService;
	
	public @ResponseBody JsonModel open(  JsonModel jsonModel, Bike bike    ) {
		
		return jsonModel;
	}
	
	

}
