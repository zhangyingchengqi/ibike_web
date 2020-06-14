package com.yc.projects.yc74ibike;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yc.projects.yc74ibike.bean.Bike;
import com.yc.projects.yc74ibike.config.AppConfig;
import com.yc.projects.yc74ibike.dao.BikeDao;
import com.yc.projects.yc74ibike.service.BikeService;

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
		Bike b = bikeDao.findBike(1L);
		b.setLatitude(20.9);
		b.setLongitude(22.2);
		b.setStatus(2);

		bikeDao.updateBike(b);
	}

	@Test
	public void testFindBike() {
		Bike b = bikeDao.findBike(1L);
		assertNotNull(b);
	}

	@Test
	public void testServiceOpen() {
		Bike b = bikeService.findByBid(1L);
		bikeService.open(b);
	}

	@Test
	public void testServiceAddNewBike() {
		Bike b = new Bike();
		Bike result = bikeService.addNewBike(b);
		System.out.println(result.getQrcode());
	}

}
