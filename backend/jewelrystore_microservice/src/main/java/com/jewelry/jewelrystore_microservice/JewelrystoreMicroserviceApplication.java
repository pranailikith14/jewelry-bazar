package com.jewelry.jewelrystore_microservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JewelrystoreMicroserviceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		System.setProperty("spring.data.mongodb.uri", dotenv.get("MONGO_URI"));

		SpringApplication.run(JewelrystoreMicroserviceApplication.class, args);
	}

}
