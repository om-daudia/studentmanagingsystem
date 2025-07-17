package com.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EntityScan("com.studentmanagement.entity")
@EnableJpaRepositories()
@EnableScheduling
@EnableFeignClients
public class StudmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudmanagementApplication.class, args);
	}

}
