package com.safetynet.appSafetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })

public class AppSafetynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSafetynetApplication.class, args);

	}
}
