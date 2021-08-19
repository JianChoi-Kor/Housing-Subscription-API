package com.project.hss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HssApplication {

	public static void main(String[] args) {
		SpringApplication.run(HssApplication.class, args);
	}

}
