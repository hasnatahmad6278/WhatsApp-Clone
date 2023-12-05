package com.whattsApp.whatsappClone.Request;

public class SingleChatReques {
	
	private Integer userId;
	
	
	public SingleChatReques() {
		
	}

	public SingleChatReques(Integer userId) {
		super();
		this.userId = userId;
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}
