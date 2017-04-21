package com.disney.studios.exceptions;

public class AlreadyVotedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyVotedException() {
		super();
	}

	public AlreadyVotedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AlreadyVotedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AlreadyVotedException(String arg0) {
		super(arg0);
	}

	public AlreadyVotedException(Throwable arg0) {
		super(arg0);
	}

}
