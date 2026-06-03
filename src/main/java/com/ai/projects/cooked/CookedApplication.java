package com.ai.projects.cooked;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class CookedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookedApplication.class, args);
	}
	
	@Bean
	public RestClient getClient() {
		return RestClient.builder()
			.baseUrl("https://hermes.ai.unturf.com/v1")
			.build();
			
	}

}
