package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
}
