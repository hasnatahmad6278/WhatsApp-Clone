package com.whattsApp.whatsappClone.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whattsApp.whatsappClone.Exception.ChatException;
import com.whattsApp.whatsappClone.Exception.UserException;
import com.whattsApp.whatsappClone.Model.Chat;
import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.ChatRepository;

@Service
public class ChatServiceImplementation implements ChatService {
	@Autowired
	private ChatRepository chatRepository;
	@Autowired
	private UserService userService;
	
	public ChatServiceImplementation (ChatRepository chatRepository, UserService userService) {
		this.chatRepository=chatRepository;
		this.userService=userService;
	}
	

	@Override
	public Chat creatChat(Integer  reqUserId, Integer userId2) throws UserException {
		// TODO Auto-generated method stub
		app_user reqUser  = userService.findUserById(reqUserId);
		app_user user = userService.findUserById(userId2);
		Chat isChatExist=chatRepository.findSingleChatByUserIds(user, reqUser);
		
		if(isChatExist!=null) {
			return isChatExist;
		}
		Chat chat= new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
		chatRepository.save(chat);
		// Log information for debugging
	    System.out.println("Creating chat: " + chat);
	    System.out.println("Users in chat: " + chat.getUsers());
	    System.out.println("User ID: " + user.getId());
	    System.out.println("ReqUser ID: " + reqUser.getId());
		return chat;
		
		
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> chat= chatRepository.findById(chatId);
		
		if(chat.isPresent()) {
			return chat.get();
		}
		
		throw new ChatException("chat not found with id" + chatId);
		
		
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		// TODO Auto-generated method stub
		
		app_user user= userService.findUserById(userId);
		List<Chat> chats=chatRepository.findChatByUserId(user.getId());
		System.out.println("User ID in findAllChatByUserId: " + user.getId());
		
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, app_user reqUser) throws UserException {
		// TODO Auto-generated method stub
		Chat group= new Chat();
		group.setGroup(true);
		group.setChat_name(req.getChat_name());
		group.setChat_image(req.getChat_image());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);
		
		for(Integer userId:req.getUserIds()) {
			app_user user =userService.findUserById(userId);
			group.getUsers().add(user);
		}
		
		chatRepository.save(group);
		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, app_user reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> opt= chatRepository.findById(chatId);

		app_user user= userService.findUserById(userId);

		if(opt.isPresent()){
		Chat chat= opt.get();
		if(chat.getAdmins().contains(reqUser)) {
			chat.getUsers().add(user);
			return chatRepository.save(chat);
		}else {
			throw new UserException("You are not Admin");
		}
		}

		throw new ChatException("chat not found with id"+chatId);
		
		
		
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, app_user reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub
		Optional<Chat> opt= chatRepository.findById(chatId);

		if(opt.isPresent()){
			Chat chat= opt.get();
			if(chat.getUsers().contains(reqUser)) {
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new ChatException("You are not member of this group");
		}
		throw new ChatException("chat not found with id"+chatId);
		
		
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, app_user reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		Optional<Chat> opt= chatRepository.findById(chatId);

		app_user user= userService.findUserById(userId);

		if(opt.isPresent()){
		Chat chat= opt.get();
		if(chat.getAdmins().contains(reqUser)) {
			chat.getUsers().remove(user);
			return chatRepository.save(chat);
		}else if (chat.getUsers().contains(reqUser)) {
			if(user.getId().equals(reqUser.getId())) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
		}
		
		
			throw new UserException("You Cannot remove another user");
		
		}

		throw new ChatException("chat not found with id"+chatId);
		
		
		
		
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		// TODO Auto-generated method stub
		
		Optional<Chat> opt= chatRepository.findById(chatId);

		if(opt.isPresent()){
		Chat chat= opt.get();
		chatRepository.deleteById(chat.getId());
		}
	}

}
