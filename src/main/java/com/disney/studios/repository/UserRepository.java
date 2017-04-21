package com.disney.studios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disney.studios.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
