package com.whattsApp.whatsappClone.Service;

import java.util.List;

import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Chat;
import com.whattsApp.whatsappClone.Model.app_user;


public interface ChatService {
	
	public Chat creatChat(Integer  reqUser, Integer userId2) throws UserException;
	
	public Chat findChatById(Integer chatId) throws ChatException;

	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

	public Chat createGroup (GroupChatRequest req, app_user reqUser) throws UserException;

	public Chat addUserToGroup (Integer userId, Integer chatId, app_user reqUser) throws UserException, ChatException;

	public Chat renameGroup (Integer chatId, String groupName, app_user reqUser) throws ChatException, UserException;

	public Chat removeFromGroup (Integer chatId, Integer userId, app_user reqUser) throws UserException, ChatException;

	public  void deleteChat (Integer chatId, Integer userId) throws ChatException, UserException;

	









}
