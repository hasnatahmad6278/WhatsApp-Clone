package com.whattsApp.whatsappClone.Service;

import java.util.List;

import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.MessageException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Message;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Request.SendmessageReques;

public interface MessageService {
	
	public Message sendMessage (SendmessageReques req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId, app_user reqUser) throws ChatException, UserException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId, app_user reqUser) throws MessageException, UserException;

}
