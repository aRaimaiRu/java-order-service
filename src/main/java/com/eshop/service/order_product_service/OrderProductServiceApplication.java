package com.eshop.service.order_product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@EnableJpaRepositories
@SpringBootApplication
public class OrderProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProductServiceApplication.class, args);
	}


}
