package com.disney.studios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.disney.studios.model.Breed;
import com.disney.studios.model.Dog;

public interface DogRepository extends JpaRepository<Dog, String>{

	@Query("select d.url from Dog d where d.breed=?" )
	public List<String>findByBreed(Breed breed);
	
}
