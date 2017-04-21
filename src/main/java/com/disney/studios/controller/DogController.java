package com.disney.studios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.disney.studios.model.Breed;
import com.disney.studios.model.Dog;
import com.disney.studios.model.Vote;
import com.disney.studios.service.DogService;

@RestController
@RequestMapping("/dogs")
public class DogController {

	@Autowired
	private DogService dogService;

	@RequestMapping(value = "/{breed}", method = RequestMethod.GET)
	public List<String> getPicturesByBreed(@PathVariable("breed") Breed breed) throws Exception {
		return dogService.findDogsByBreed(breed);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<Breed, List<String>> getAllDogPicturesByBreed() throws Exception {
		return dogService.findAllBreedsWithPictures();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Dog upOrDownVote(@RequestBody Vote vote) throws Exception{
		return dogService.vote(vote);
	}

}
