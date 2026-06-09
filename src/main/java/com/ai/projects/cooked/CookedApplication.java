package com.ai.projects.cooked;

import org.springframework.beans.factory.annotation.Value;
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
	public RestClient getClient(
			@Value("${gemini.base.url}") String baseUrl,
			@Value("${gemini.api.key}") String apiKey) 
	{
		return RestClient.builder()
			.baseUrl(baseUrl)
			.defaultHeader("x-goog-api-key", apiKey)
			.build();
			
	}

}
