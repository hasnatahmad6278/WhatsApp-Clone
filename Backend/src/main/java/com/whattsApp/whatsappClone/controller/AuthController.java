package com.whattsApp.whatsappClone.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.UserRepository;
import com.whattsApp.whatsappClone.Request.LoginRequest;
import com.whattsApp.whatsappClone.Response.AuthResponse;
import com.whattsApp.whatsappClone.Service.CustomUserService;
import com.whattsApp.whatsappClone.Service.UserService;
import com.whattsApp.whatsappClone.config.TokenProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserService customerService;
	
	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.tokenProvider=tokenProvider;
		this.customUserService=customUserService;
	}
	
	
	
	@PostMapping("/signup")
	public AuthResponse createUser (@RequestBody app_user user) throws Exception{
		app_user isExist=userRepository.findByEmail(user.getEmail());
		if(isExist!=null) {
			throw new Exception("this email already with another account");
		}
		
		app_user newUser=new app_user();
		newUser.setEmail(user.getEmail());
		  newUser.setFull_name(user.getFull_name());
		  newUser.setPassword(passwordEncoder.encode(user.getPassword()));
          app_user savedUser=userRepository.save(newUser);
		  
		  Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		  
		  String token= TokenProvider.generateToken(authentication);
		  AuthResponse res= new AuthResponse(token, "Register Success");
		  return res;
	}
	
	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
		Authentication authentication= authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		
		 String token= TokenProvider.generateToken(authentication);
		  AuthResponse res= new AuthResponse(token, "Login Success");
		  return res;
		  
	}


	
	private Authentication authenticate(String email, String password) {
		// TODO Auto-generated method stub
		
		UserDetails userDetails=customUserService.loadUserByUsername(email);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("password not match");
			
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	
}
	
	
	
	
