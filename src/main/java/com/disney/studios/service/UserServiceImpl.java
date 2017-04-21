package com.disney.studios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.studios.model.User;
import com.disney.studios.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository UserRepository;
	
	@Override
	public User createUser(User user) {
		return UserRepository.save(user);
	}

}
