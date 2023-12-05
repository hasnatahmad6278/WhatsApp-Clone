package com.whattsApp.whatsappClone.Service;

import java.util.List;

import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Request.updateUserRequest;

public interface UserService {
	
	public app_user findUserById(Integer id) throws UserException;
	public app_user findUserProfile(String jwt) throws UserException;
	public app_user updateUser(Integer userId, updateUserRequest req) throws UserException;
	public List<app_user> searchUser(String query);
	public app_user registerUser(app_user user);

}
