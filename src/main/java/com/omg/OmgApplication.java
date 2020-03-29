package com.omg;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.omg.mapper.UserMapper;
import com.omg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.omg.mapper")
@EnableConfigurationProperties
@NacosPropertySource(dataId = "omg",autoRefreshed = true)
public class OmgApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(OmgApplication.class, args);
	}

	@Autowired
	private  UserMapper userMapper;
	@Autowired
	UserServiceImpl userService;
	@Override
	public void run(String... args) throws Exception {
		userService.init();
	}
}
