package com.whattsApp.whatsappClone.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.UserRepository;
import com.whattsApp.whatsappClone.Request.updateUserRequest;
import com.whattsApp.whatsappClone.Response.AuthResponse;
import com.whattsApp.whatsappClone.config.TokenProvider;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenProvider tokenProvider;
	
	
	public UserServiceImplementation (UserRepository userRepository, TokenProvider tokenProvider) {
		this.userRepository=userRepository;
		this.tokenProvider=tokenProvider;
	}

	@Override
	public app_user findUserById(Integer id) throws UserException {
		// TODO Auto-generated method stub
		Optional<app_user> opt= userRepository.findById(id);
		if(opt.isPresent()) {
		return opt.get();
		}
		throw new UserException("User Not Found with id"+ id);
	}

	@Override
	public app_user findUserProfile(String jwt) throws UserException{
		// TODO Auto-generated method stub
		String email= tokenProvider.getEmailFromToken(jwt);
		if(email==null) {
			throw new BadCredentialsException("Received Invalid Token");
		}
		app_user user = userRepository.findByEmail(email);
		if(user==null) {
		throw new UserException("User not found with email" +email);
			
		}
		return user;
	}

	@Override
	public app_user updateUser(Integer userId, updateUserRequest req) throws UserException {
		// TODO Auto-generated method stub
		
		app_user user =findUserById(userId);

		if(req.getFull_name()!=null){
		user.setFull_name(req.getFull_name());
		}

		if(req.getProfile_picture()!=null){
		user.setProfile_picture(req.getProfile_picture());
		}

		return userRepository.save(user);


		
	}

	@Override
	public List<app_user> searchUser(String query) {
		// TODO Auto-generated method stub
		List<app_user> users= userRepository.searchUser(query);
		return users;
	}
	
	@Override
	public app_user registerUser(app_user user) {
		// TODO Auto-generated method stub
		  app_user newUser=new app_user();
		  newUser.setEmail(user.getEmail());
		  newUser.setFull_name(user.getFull_name());
		  newUser.setPassword(user.getPassword());
		  newUser.setId(user.getId());
		  newUser.setProfile_picture(user.getProfile_picture());
		  
		  app_user savedUser=userRepository.save(newUser);
		  
		
		  
		  
		return savedUser;
	}
	

}
