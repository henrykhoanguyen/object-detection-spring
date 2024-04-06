package com.take.home;

import com.take.home.repository.ImageRepository;
import com.take.home.repository.ObjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ObjectDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjectDetectionApplication.class, args);
	}
}
