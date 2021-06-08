package com.fundoo.util;

import lombok.Data;

public @Data class Response {

	private Integer StatusCode;
	private String StatusMessage;
	private Object token;
	
	public Response(Integer statusCode, String statusMessage, Object token) {
		super();
		StatusCode = statusCode;
		StatusMessage = statusMessage;
		this.token = token;
	}
	
}
