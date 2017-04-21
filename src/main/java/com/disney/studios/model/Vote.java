package com.disney.studios.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vote {

	@Id
	private VoteId voteId;
	
	private VoteType voteType;
	
	public VoteType getVoteType() {
		return voteType;
	}
	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public VoteId getVoteId() {
		return voteId;
	}

	public void setVoteId(VoteId voteId) {
		this.voteId = voteId;
	}

}
