package com.yc.projects.yc74ibike.config;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages = "com.yc")
@EnableTransactionManagement // 查找spring 托管Bean 上是否有
								// @Transactional注解,如果有，则启用事务管理器
								// @Transactional(value="tx")
public class AppConfig {

	private Logger log = Logger.getLogger(AppConfig.class);
	
	
	@Bean   // 键[字符串]: 值[对象]
	public RedisTemplate redsiTemplate() {
		JedisConnectionFactory conn = new JedisConnectionFactory();
        conn.setDatabase(0);
        conn.setHostName("192.168.0.200");
        //conn.setPort(6379);
        conn.setPort( 6381 );      //一个单独的一个redis服务器。
        conn.setPassword("");
        conn.setUsePool(true);
        conn.afterPropertiesSet();
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;
	}
	
	@Bean     // 键[字符串]: 值[字符串]
	public StringRedisTemplate stringRedisTemplate() {
		JedisConnectionFactory conn = new JedisConnectionFactory();
        conn.setDatabase(0);
        conn.setHostName("192.168.0.200");
        //conn.setPort(6379);
        conn.setPort( 6381 );
        conn.setPassword("");
        conn.setUsePool(true);
        conn.afterPropertiesSet();
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;
	}
	
	
	@Bean    //  MongoTemplate由spring 托管
    @Primary
    public MongoTemplate template() {
        return new MongoTemplate(factory());
    }
	
	 /**
     * 功能描述: 创建数据库名称对应的工厂，数据库名称可以通过配置文件导入
     * @param
     * @return:org.springframework.data.mongodb.MongoDbFactory
     * @since: v1.0
     */
    @Bean("mongoDbFactory")
    public MongoDbFactory factory() {
        return new SimpleMongoDbFactory(client(), "yc74ibike");
    }
    
    /**
     * 功能描述: 配置client，client中传入的ip和端口可以通过配置文件读入
     *
     * @param
     * @return:com.mongodb.MongoClient
     */
    @Bean("mongoClient")
    public MongoClient client() {
        return new MongoClient("192.168.0.200", 27017);
    }

	@Bean // TODO: 修改成数据库联接池, 获取数据源
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/ibike");
		dataSource.setUsername("root");
		dataSource.setPassword("a");
		log.info("创建数据源" + dataSource);
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager tx(DriverManagerDataSource ds) {
		log.info("创建事务管理器," + ds);
		DataSourceTransactionManager dtm = new DataSourceTransactionManager();
		dtm.setDataSource(ds);
		return dtm;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setIgnoreUnresolvablePlaceholders(true);
		return c;
	}

}
