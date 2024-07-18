package com.codechallenge.customermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableFeignClients
@EnableJpaRepositories("com.codechallenge.customermanagement.*")
@EntityScan("com.codechallenge.customermanagement.*")
public class CustomerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}

}
