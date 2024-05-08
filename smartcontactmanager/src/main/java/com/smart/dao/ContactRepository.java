package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Contact;
import com.smart.entity.User;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	// Here we will have to add Pageable to track the pages of the content
	// Pageable track two things :-
	// Current Page 
	// Contacts per Page 
	@Query("from Contact as contact where contact.user.id=:userId")
	
	public Page<Contact> getContactById(@Param("userId")int userId,Pageable pageable);
	
	
	public List<Contact> findByNameContainingAndUser(String query, User user);
}
