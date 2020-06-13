package com.yc.projects.yc74ibike.config;


import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages="com.yc")   
@EnableTransactionManagement    //查找spring  托管Bean 上是否有 @Transactional注解,如果有，则启用事务管理器   @Transactional(value="tx")
public class AppConfig {
	
	private Logger log=Logger.getLogger(AppConfig.class);
	

	@Bean   //TODO: 修改成数据库联接池, 获取数据源
	public  DriverManagerDataSource   dataSource(       ) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/ibike");
		dataSource.setUsername("root");
		dataSource.setPassword("a");
		log.info(   "创建数据源"+ dataSource );
		return dataSource;
	}
	
	@Bean
	public    DataSourceTransactionManager  tx(  DriverManagerDataSource ds    ){
		log.info(   "创建事务管理器,"+  ds );
		DataSourceTransactionManager dtm=new DataSourceTransactionManager();
		dtm.setDataSource(   ds );
		return dtm;
	}
	
	
}
