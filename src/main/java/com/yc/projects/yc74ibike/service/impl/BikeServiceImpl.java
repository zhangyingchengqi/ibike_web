package com.yc.projects.yc74ibike.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yc.projects.yc74ibike.bean.Bike;
import com.yc.projects.yc74ibike.dao.BikeDao;
import com.yc.projects.yc74ibike.service.BikeService;

import io.swagger.annotations.Api;

@Service
@Transactional
@Api(value="小辰出行单车信息操作业务",tags= {"业务层"})
public class BikeServiceImpl implements BikeService {
	@Autowired
	private BikeDao bikeDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	private Logger logger = LogManager.getLogger();

	@Override
	public void open(Bike bike) {
		if (bike.getBid() == null) {
			throw new RuntimeException("缺少待开没单车编号");
		}
		Bike b = findByBid(bike.getBid());
		if (b == null) {
			throw new RuntimeException("查无此车");
		}
		switch (b.getStatus()) {
		case Bike.UNACTIVE:
			throw new RuntimeException("此车暂未启用，请更换一辆");
		case Bike.USING:
			throw new RuntimeException("此车正在骑行中，请更换一部...");
		case Bike.INTROUBLE:
			throw new RuntimeException("此单车待维修，请更换一部");
		}
		bike.setStatus(  Bike.USING );
		bikeDao.updateBike(bike);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bike findByBid(Long bid) {
		Bike b = null;
		try {
			b = bikeDao.findBike(bid);
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		// 复杂业务
		return b;
	}

	@Override
	public Bike addNewBike(Bike bike) {
		Bike b = bikeDao.addBike(bike); // 先insert
		Long bid = b.getBid();
		bike = findByBid(bid);
		// TODO: 根据bid生成二维码
		String qrcode = bid + "";
		bike.setQrcode(qrcode);
		bikeDao.updateBike(bike); // 再更新
		return bike;
	}

	@Override
	public List<Bike> findNearAll(Bike bike) {
		//  db.bike.find(      {loc:{$near:[28.189122,112.943867]}, status:1} ) 
		Query query=new Query();
		query.addCriteria(  Criteria.where("status").is(  bike.getStatus()  )     )
		     .addCriteria(  Criteria.where("loc").near(new Point( bike.getLongitude(),bike.getLatitude()) ))
		     .limit(10);
		
		//   查出来的json结构: { "_id" : 100001, "status" : 1, "loc" : [ 28.189133, 112.943868 ], "qrcode" : "" }   
		List<Bike> list= this.mongoTemplate.find(query, Bike.class, "bike");
		
		for(  Bike b:list) {
			b.setBid(    b.getId() );
			b.setId(null);
			b.setLongitude(     b.getLoc()[1]);
			b.setLatitude(   b.getLoc()[0]);
			b.setLoc(null);
		}
		return list;
	}

}











