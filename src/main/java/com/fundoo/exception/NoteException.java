package com.fundoo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ResponseStatus
@Data
public class NoteException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private int StatusCode;
	private String StatusMessage;
	
	public NoteException(int statusCode, String statusMessage) {
		super();
		StatusCode = statusCode;
		StatusMessage = statusMessage;
	}
}
