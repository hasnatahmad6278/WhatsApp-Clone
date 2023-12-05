package com.whattsApp.whatsappClone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whattsApp.whatsappClone.DTO.ChatDTO;
import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Chat;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Request.SingleChatReques;
import com.whattsApp.whatsappClone.Response.ApiResponse;
import com.whattsApp.whatsappClone.Service.ChatService;
import com.whattsApp.whatsappClone.Service.GroupChatRequest;
import com.whattsApp.whatsappClone.Service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	private UserService userService;
	
	public ChatController (ChatService chatService, UserService userService) {
		this.chatService=chatService;
		this.userService=userService;
	}
	
	
	 
	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatReques singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
	    System.out.println("Single Chat...............................");
	    try {
	        app_user reqUser = userService.findUserProfile(jwt);
	        System.out.println("Authenticated User<------------->: " + reqUser.getFull_name()+ "<----------------->"); // Log the authenticated user
	        Integer userId = singleChatRequest.getUserId();
	        
	       // Integer reInteger=reqUser.getId();
	        System.out.println("Received userId: " + userId);
	        Chat chat = chatService.creatChat(reqUser.getId(), userId);
	        
	        // Log the created chat
	        System.out.println("Created Chat: " + chat);
	        return new ResponseEntity<>(chat, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for further investigation
	        return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
	    }  
	}

	

	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHanndler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization")String jwt) throws UserException{

		app_user reqUser= userService.findUserProfile(jwt);
		Chat chat= chatService.createGroup(groupChatRequest, reqUser);
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);

	}
	
	@PostMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHanndler(@PathVariable Integer chatId) throws  ChatException{

	
		Chat chat= chatService.findChatById(chatId);
		
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);

	}
	
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserIdHanndle( @RequestHeader("Authorization")String jwt) throws UserException{

		app_user user= userService.findUserProfile(jwt);
		 System.out.println("User ID in /api/chats/gtuser request: " + user.getId());
		List<Chat> chats= chatService.findAllChatByUserId(user.getId());
		System.out.println("Chats retrieved: " + chats);
		return new ResponseEntity<List<Chat>>(chats, HttpStatus.ACCEPTED);
		

	}
	
	@PostMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHanndle(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{

		app_user reqUser= userService.findUserProfile(jwt);
		Chat chats= chatService.addUserToGroup(userId, chatId, reqUser);
		
		return new ResponseEntity<>(chats, HttpStatus.OK);

	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroupHanndle(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{

		app_user reqUser= userService.findUserProfile(jwt);
		Chat chat= chatService.removeFromGroup(chatId, userId, reqUser);
		
		return new ResponseEntity<>(chat, HttpStatus.OK);

	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHanndle(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException{

		app_user reqUser= userService.findUserProfile(jwt);
		 chatService.deleteChat(chatId, reqUser.getId());
		 ApiResponse res= new ApiResponse("chat is deleted succesfully", false);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}
	
	
	

}
