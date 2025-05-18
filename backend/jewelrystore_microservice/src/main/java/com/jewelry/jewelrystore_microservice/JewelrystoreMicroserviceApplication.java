package com.jewelry.jewelrystore_microservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class JewelrystoreMicroserviceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		System.setProperty("spring.data.mongodb.uri", dotenv.get("MONGO_URI"));
		System.setProperty("spring.profiles.active", dotenv.get("SPRING_PROFILES_ACTIVE"));

		SpringApplication.run(JewelrystoreMicroserviceApplication.class, args);
	}

}
