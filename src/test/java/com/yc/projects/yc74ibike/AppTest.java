package com.yc.projects.yc74ibike;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yc.projects.yc74ibike.bean.Bike;
import com.yc.projects.yc74ibike.config.AppConfig;
import com.yc.projects.yc74ibike.dao.BikeDao;
import com.yc.projects.yc74ibike.service.BikeService;
import com.yc.projects.yc74ibike.service.UserService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class AppTest extends TestCase {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private BikeDao bikeDao;
	@Autowired
	private BikeService bikeService;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testUserService() throws Exception {
		userService.genVerifyCode("86", "15386490869");
	}
	
	@Test
	public void testRedisTemplate() {
		System.out.println(    redisTemplate);
	}
	
	
	@Test 
	public void testNearBikes() {
		Bike b=new Bike();
		b.setLatitude(28.189122);
		b.setLongitude(112.943867);
		b.setStatus(1);
		List<Bike> list=bikeService.findNearAll(b);
		System.out.println(  list );
	}
	
	@Test
	public void testMongoTemplate() {
		System.out.println( mongoTemplate.getDb().getName() );
		System.out.println(  mongoTemplate.getCollectionNames()  );
	}

	@Test
	public void testDataSource() throws SQLException {
		assertNotNull(dataSource);
		assertNotNull(dataSource.getConnection());
	}

	@Test
	public void testAddNewBike() {
		Bike b = new Bike();
		Bike result = bikeDao.addBike(b);
		assertNotNull(result.getBid());
		System.out.println(result.getBid());
	}

	@Test
	public void testUpdateBike() {
		Bike b = bikeDao.findBike(1L+"");
		b.setLatitude(20.9);
		b.setLongitude(22.2);
		b.setStatus(2);

		bikeDao.updateBike(b);
	}

	@Test
	public void testFindBike() {
		Bike b = bikeDao.findBike(1L+"");
		assertNotNull(b);
	}

	@Test
	public void testServiceOpen() {
		Bike b = bikeService.findByBid(1L+"");
		bikeService.open(b);
	}

	@Test
	public void testServiceAddNewBike() {
		Bike b = new Bike();
		Bike result = bikeService.addNewBike(b);
		System.out.println(result.getQrcode());
	}

}
