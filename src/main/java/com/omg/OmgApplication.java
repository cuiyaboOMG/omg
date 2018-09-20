package com.omg;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.omg.mapper")
public class OmgApplication {
	public static void main(String[] args) {
		SpringApplication.run(OmgApplication.class, args);
	}
}
