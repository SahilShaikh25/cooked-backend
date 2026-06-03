package com.ai.projects.cooked.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ai.projects.cooked.model.ChatBody;
import com.ai.projects.cooked.model.ChatResponse;
import com.ai.projects.cooked.model.MessageBody;

@Service
public class ChatService {

	private RestClient restClient;
	public ChatService(RestClient restClient) {
		this.restClient = restClient;
	}	
	
	public String getResponse(String profileText, String roastLevel) {
		
		String prompt = getRoastPrompt(profileText, roastLevel);
		
		ChatBody chatBody = new ChatBody(prompt, "user");
		MessageBody messageBody = new MessageBody(List.of(chatBody));

		ChatResponse response = restClient.post()
							.uri("/chat/completions")
							.contentType(MediaType.APPLICATION_JSON)
							.body(messageBody)
							.retrieve()
							.body(ChatResponse.class);
		
		return response
				.getChoices()
				.get(0)
				.getMessage()
				.getContent();
	}
	
	private String getRoastPrompt(String profileText, String roastLevel) {
		
		switch(roastLevel) {
		case "rare":
			return """
				You are a comedian.

                Roast this LinkedIn profile lightly.
                Keep it playful and funny.
                Do not be mean.
                Focus on buzzwords, job titles and exaggerated claims.

                Profile:
				""" + profileText;
		case "medium-rare":
			return """
				You are a professional roast comedian.

                Roast this LinkedIn profile aggressively.
                Be sarcastic and savage.
                Mock corporate buzzwords and self-promotion.

                Profile:
                """ + profileText;
		case "charcoal":
			return """
				You are the most ruthless roast comedian alive.

                Completely destroy this LinkedIn profile.
                Be extremely funny and brutal.
                Mock every buzzword, humblebrag, certification and achievement.
                Use creative comparisons and absurd humor.

                IMPORTANT:
                Keep it humorous and non-hateful.
                Do not attack protected characteristics.

                Profile:
                """ + profileText;
		default:
	        throw new IllegalArgumentException(
	                "Invalid roast level"
	        );
		}
		
	}
}
