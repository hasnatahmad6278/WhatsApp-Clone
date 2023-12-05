package com.whattsApp.whatsappClone.Service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whattsApp.whatsappClone.Model.app_user;
import com.whattsApp.whatsappClone.Repository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	/*public CustomUserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}*/
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		app_user user= userRepository.findByEmail(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email : " + username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new  org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	
	}

}
