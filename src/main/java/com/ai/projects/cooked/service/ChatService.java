package com.ai.projects.cooked.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ai.projects.cooked.model.ChatBody;
import com.ai.projects.cooked.model.ChatResponse;
import com.ai.projects.cooked.model.MessageBody;
import com.ai.projects.cooked.model.geminidto.request.GeminiContent;
import com.ai.projects.cooked.model.geminidto.request.GeminiPart;
import com.ai.projects.cooked.model.geminidto.request.GeminiRequest;
import com.ai.projects.cooked.model.geminidto.response.GeminiResponse;

@Service
public class ChatService {

	private RestClient restClient;
	public ChatService(RestClient restClient) {
		this.restClient = restClient;
	}	
	
	public String getResponse(String profileText, String roastLevel) {
		
		String prompt = getRoastPrompt(profileText, roastLevel);

		try {
			
			return callGemini(prompt, "gemini-3.5-flash");
			
		}catch (Exception e) {
			
			System.out.println("Initial connection failed. Re-connecting....");
			
			try {
			
				return callGemini(prompt, "gemini-3.1-flash-lite");
			
			}catch (Exception ex) {
				
				throw new RuntimeException(
						"Third Party Service Not Available. Please try again later...."
				);
				
			}
			
		}
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
	
	private String callGemini(
	        String prompt,
	        String model) {

	    GeminiRequest request =
	            new GeminiRequest(
	                    List.of(
	                            new GeminiContent(
	                                    List.of(
	                                            new GeminiPart(prompt)
	                                    )
	                            )
	                    )
	            );

	    GeminiResponse response =
	            restClient.post()
	                    .uri(
	                            "/v1beta/models/"
	                                    + model
	                                    + ":generateContent"
	                    )
	                    .contentType(
	                            MediaType.APPLICATION_JSON
	                    )
	                    .body(request)
	                    .retrieve()
	                    .body(GeminiResponse.class);

	    return response.candidates()
	            .get(0)
	            .content()
	            .parts()
	            .get(0)
	            .text();
	}
}
