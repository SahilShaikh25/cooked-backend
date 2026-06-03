package com.ai.projects.cooked.model;

import java.util.List;

public class MessageBody {

	private List<ChatBody> messages;
	
	public MessageBody() {}

	public MessageBody(List<ChatBody> messages) {
		this.messages = messages;
	}

	public List<ChatBody> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatBody> messages) {
		this.messages = messages;
	}

	

	
	
}
