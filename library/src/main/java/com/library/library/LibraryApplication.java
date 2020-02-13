package com.library.library;

import com.library.library.properties.ExcludeUrlsProperties;
import com.library.library.utils.JwtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableConfigurationProperties({ExcludeUrlsProperties.class, JwtUtil.class})
@MapperScan("com.library.library.mapper")
@EnableCaching
public class LibraryApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	private Logger logger = LoggerFactory.getLogger(LibraryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
	}

	private void showConnection() throws SQLException {
		logger.info("获取连接 +{}",dataSource.getConnection().toString());
	}

}
