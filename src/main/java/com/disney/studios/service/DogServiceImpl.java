package com.disney.studios.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disney.studios.exceptions.AlreadyVotedException;
import com.disney.studios.model.Breed;
import com.disney.studios.model.Dog;
import com.disney.studios.model.Vote;
import com.disney.studios.repository.DogRepository;
import com.disney.studios.repository.VoteRepository;


@Service
public class DogServiceImpl implements DogService{
	
	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private VoteRepository voteRepository;

	public List<String>findDogsByBreed(Breed breed)throws Exception{
		return dogRepository.findByBreed(breed);
	}

	@Override
	public Map<Breed, List<String>> findAllBreedsWithPictures() throws Exception{
		List<Dog>dogs = dogRepository.findAll();
		Map<Breed, List<String>> breedMap = dogs.parallelStream()
				.collect( Collectors.groupingBy(Dog::getBreed, Collectors.mapping(Dog::getUrl, Collectors.toList())));
		return breedMap;
	}

	@Override
	public Dog vote(Vote vote) throws Exception{
		Dog dog = dogRepository.findOne(vote.getVoteId().getUrl());
		Vote dbVote = voteRepository.findOne(vote.getVoteId());
		if(dbVote == null){
			Vote newVote = new Vote();
			newVote.setVoteId(vote.getVoteId());
			newVote.setVoteType(vote.getVoteType());
			voteRepository.save(newVote);
		}else{
			throw new AlreadyVotedException("You have already voted for this dog");
		}
		List<Vote>votes = voteRepository.findAll();
		System.out.println(votes.size());
		dog.setUpCount(voteRepository.getFavouriteCount(vote.getVoteId().getUrl(), vote.getVoteType()));
		return dog;
	}

}
