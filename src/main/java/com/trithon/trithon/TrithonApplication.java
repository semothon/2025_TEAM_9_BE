package com.trithon.trithon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.trithon.trithon.config")
@ComponentScan(basePackages = "com.trithon.trithon")
public class TrithonApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrithonApplication.class, args);
	}
}
