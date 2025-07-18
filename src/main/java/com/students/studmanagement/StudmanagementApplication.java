package com.students.studmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.students.studmanagement.entity")
@EnableJpaRepositories("com.students.studmanagement.repository")
@EnableScheduling
@EnableFeignClients(basePackages = "com.students.studmanagement.fiegnclient")
public class StudmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudmanagementApplication.class, args);
	}

}
