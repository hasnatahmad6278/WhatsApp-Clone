package com.whattsApp.whatsappClone.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whattsApp.whatsappClone.Model.app_user;



public interface UserRepository extends JpaRepository<app_user, Integer> {
	
	public app_user findByEmail(String email);
	
	@Query("select u from app_user u where u.full_name Like %:query% or u.email Like %:query%")
	public List<app_user>searchUser(@Param("query") String query);

}
