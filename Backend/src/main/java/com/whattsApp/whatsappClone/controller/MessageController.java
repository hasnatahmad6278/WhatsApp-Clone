package com.whattsApp.whatsappClone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.MessageException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Message;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Request.SendmessageReques;
import com.whattsApp.whatsappClone.Response.ApiResponse;
import com.whattsApp.whatsappClone.Service.MessageService;
import com.whattsApp.whatsappClone.Service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendmessageReques req, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{
		 System.out.println("Message...............................");
		app_user user= userService.findUserProfile(jwt);
		
		System.out.println("Authenticated User<------------->: " + user.getFull_name()+ "<----------------->");
		
		req.setUserId(user.getId());
		
		Message message= messageService.sendMessage(req);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatsMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{
		app_user user= userService.findUserProfile(jwt);
		List<Message> messages= messageService.getChatsMessages(chatId, user);
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
	

	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException, MessageException{
		app_user user= userService.findUserProfile(jwt);
		messageService.deleteMessage(messageId, user);
		
		ApiResponse res= new ApiResponse("Message deleted successfully", false);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	

}
