package com.whattsApp.whatsappClone.DTO;

import java.util.List;
import java.util.Set;

public class ChatDTO {
    
    private Integer id;
    private String chatName;
    private String chatImage;
    private boolean isGroup;
    private String createdBy; // Assuming you want to store the email or username of the creator
    private Set<String> admins; // Assuming you want to store the emails or usernames of admins
    private Set<String> users; // Assuming you want to store the emails or usernames of users
    
    // Constructors, getters, and setters

    // Constructors
    public ChatDTO() {
    }

	public ChatDTO(Integer id, String chatName, String chatImage, boolean isGroup, String createdBy, Set<String> admins,
			Set<String> users) {
		super();
		this.id = id;
		this.chatName = chatName;
		this.chatImage = chatImage;
		this.isGroup = isGroup;
		this.createdBy = createdBy;
		this.admins = admins;
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public String getChatImage() {
		return chatImage;
	}

	public void setChatImage(String chatImage) {
		this.chatImage = chatImage;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Set<String> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<String> admins) {
		this.admins = admins;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}
    
    

}