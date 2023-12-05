package com.whattsApp.whatsappClone.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.UserRepository;
import com.whattsApp.whatsappClone.Request.updateUserRequest;
import com.whattsApp.whatsappClone.Response.ApiResponse;
import com.whattsApp.whatsappClone.Service.UserService;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/pusers")
   public app_user createUser(@RequestBody app_user user) {
	   app_user savedUser=userService.registerUser(user);
	   return savedUser;
   }
	
	@GetMapping("/pusers")
	public List<app_user> getUsers(){
		List<app_user> users=userRepository.findAll();
		return users;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<app_user> getUserProfileHandler(@RequestHeader ("Authorization") String token) throws UserException{
		System.out.println("req profile--------------");
		app_user user= userService.findUserProfile(token);
		
		System.out.println("req profile"+user.getEmail());
		return new  ResponseEntity<app_user>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<HashSet<app_user>> searchUsersByName(@RequestParam ("name") String name) {
		System.out.println("search user....................");
		List<app_user> users=userService.searchUser(name);
		
		HashSet<app_user> set=new HashSet<>(users);
		
		
	    System.out.println("search result ------" +set);
		return new  ResponseEntity<>(set,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/{query}")
	public ResponseEntity<List<app_user>> searchUserHandler(@PathVariable ("query") String q){
		List<app_user> users= userService.searchUser(q);
		return new  ResponseEntity<List<app_user>>(users,HttpStatus.OK);
		
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<app_user> updateUserHandler(@RequestBody updateUserRequest req, @PathVariable Integer userId) throws UserException{
		System.out.println("Update User...................");
		app_user updatedUser= userService.updateUser(userId, req);
		
		return new  ResponseEntity<app_user>(updatedUser,HttpStatus.OK);
		
	}
	
	
	

}
