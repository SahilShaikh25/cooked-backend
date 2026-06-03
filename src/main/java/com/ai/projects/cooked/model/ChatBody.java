package com.ai.projects.cooked.model;

public class ChatBody {

	private String content;
	private String role;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public ChatBody(String content, String role) {
		super();
		this.content = content;
		this.role = role;
	}
	
	
	
	
}
