package com.whattsApp.whatsappClone.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.MessageException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Chat;
import com.whattsApp.whatsappClone.Model.Message;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.MessageRepository;
import com.whattsApp.whatsappClone.Request.SendmessageReques;

@Service
public class MessageServiceImplementation implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ChatService chatService;
	
	
	@Override
	public Message sendMessage(SendmessageReques req) throws UserException, ChatException {
		// TODO Auto-generated method stub
		app_user user=userService.findUserById(req.getUserId());
		Chat chat= chatService.findChatById(req.getChatId());
		Message message= new Message();
		
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		
		messageRepository.save(message);
		
		// Add the message to the chat and save the chat
	   // chat.getMessages().add(message);
	    //chatRepository.save(chat);
		
		return message;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId, app_user reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub
		
		Chat chat= chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("You are not related to this chat" +chat.getId());
		}
		
		List<Message> messages=messageRepository.findByChatId(chatId);
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		// TODO Auto-generated method stub
		Optional<Message> opt=messageRepository.findById(messageId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		
		throw new MessageException("Message Not found with id" + messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, app_user reqUser) throws MessageException, UserException {
		// TODO Auto-generated method stub
		
		Message message= findMessageById(messageId);
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		
		throw new UserException("Youc an delete another user's message"+reqUser.getFull_name());
		
	}

}
