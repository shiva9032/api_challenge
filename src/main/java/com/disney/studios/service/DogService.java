package com.disney.studios.service;

import java.util.List;
import java.util.Map;

import com.disney.studios.model.Breed;
import com.disney.studios.model.Dog;
import com.disney.studios.model.Vote;

public interface DogService {

	public List<String>findDogsByBreed(Breed breed) throws Exception;
	
	public Map<Breed, List<String>>findAllBreedsWithPictures() throws Exception;
	
	public Dog vote(Vote vote) throws Exception;
}
