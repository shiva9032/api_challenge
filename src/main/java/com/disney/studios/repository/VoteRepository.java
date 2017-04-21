package com.disney.studios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.disney.studios.model.Vote;
import com.disney.studios.model.VoteId;
import com.disney.studios.model.VoteType;

public interface VoteRepository extends JpaRepository<Vote, VoteId>{

	@Query(value="select count(*) from Vote where voteId.url=? and voteType=?")
	public Long getFavouriteCount(String url, VoteType voteType);
}
