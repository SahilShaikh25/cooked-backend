package com.ai.projects.cooked.model;

import java.util.List;

public class ChatResponse {

	private List<Choices> choices;
	
	public ChatResponse() {

	}

	public List<Choices> getChoices() {
		return choices;
	}

	public void setChoices(List<Choices> choices) {
		this.choices = choices;
	}
	
	
}
