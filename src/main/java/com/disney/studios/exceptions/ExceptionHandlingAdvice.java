package com.disney.studios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice {

	@ExceptionHandler(AlreadyVotedException.class)
	public ResponseEntity<String> handleVoteAlreadyExistsException(AlreadyVotedException ae){
		return new ResponseEntity<String>(ae.getMessage(),HttpStatus.NOT_MODIFIED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleVoteAlreadyExistsException(Exception ae){
		return new ResponseEntity<String>(ae.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
