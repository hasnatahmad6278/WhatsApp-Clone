package com.whattsApp.whatsappClone.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.whattsApp.whatsappClone.Model.Message;



public class RealtimeChat {
	
	private SimpMessagingTemplate simpleMessageTemplate;
	
	@MessageMapping("/message")
	@SendTo("/goup/public")
	public Message reciveMessage(@Payload Message message) {
		simpleMessageTemplate.convertAndSend("/goup/"+ message.getChat().getId().toString(), message);
		return message;
	}
	
	
	

}
