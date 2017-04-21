package com.disney.studios.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class VoteId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public VoteId(String url, Long userId) {
		super();
		this.url = url;
		this.userId = userId;
	}
	
	public VoteId(){
		
	}
	

	private String url;
	private Long userId;
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
