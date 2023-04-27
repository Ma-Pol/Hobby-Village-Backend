package com.hobbyvillage.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hobbyvillage.backend")
public class HobbyvillageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HobbyvillageBackendApplication.class, args);
	}

}
