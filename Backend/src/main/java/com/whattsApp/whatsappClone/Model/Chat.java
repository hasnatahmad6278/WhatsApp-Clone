
package com.whattsApp.whatsappClone.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.whattsApp.whatsappClone.Model.Message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Chat {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer id;
	

	private String chat_name;
	
	private String chat_image;
	
	@ManyToMany
	private Set<app_user> admins= new HashSet<app_user>();
	
	
	@Column(name = "is_group")
	private boolean isGroup;
	
	@JoinColumn(name = "created-by")
	@ManyToOne
	private app_user createdBy;
	
	@ManyToMany
	private Set<app_user> users= new HashSet<app_user>();
	
	@OneToMany
	private List<Message> messages= new ArrayList<>();
	
	public Chat() {
		
	}



	public Set<app_user> getAdmins() {
		return admins;
	}



	public void setAdmins(Set<app_user> admins) {
		this.admins = admins;
	}



	public Chat(Integer id, String chat_name, String chat_image, Set<app_user> admins, boolean isGroup, app_user createdBy,
			Set<app_user> users, List<Message> messages) {
		super();
		this.id = id;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
		this.admins = admins;
		this.isGroup = isGroup;
		this.createdBy = createdBy;
		this.users = users;
		this.messages = messages;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChat_name() {
		return chat_name;
	}

	public void setChat_name(String chat_name) {
		this.chat_name = chat_name;
	}

	public String getChat_image() {
		return chat_image;
	}

	public void setChat_image(String chat_image) {
		this.chat_image = chat_image;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public app_user getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(app_user createdBy) {
		this.createdBy = createdBy;
	}

	public Set<app_user> getUsers() {
		return users;
	}

	public void setUsers(Set<app_user> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	
	

}
