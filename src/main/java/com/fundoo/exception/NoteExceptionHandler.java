package com.fundoo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fundoo.util.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class NoteExceptionHandler {

private static final String message = "Exception while processing REST Request";
	
	private static final int errorCode = 400;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleMethodArgumentNotValidException(
										MethodArgumentNotValidException exception) {
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errorMsg = errorList.stream()
								.map(objErr -> objErr.getDefaultMessage())
								.collect(Collectors.toList());
		log.error("Error msg:" + message + errorMsg);
		Response response = new Response(errorCode, message, errorMsg);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleContactRegisterException(
										NoteException exception) {
		log.error("Error msg:" + message + exception.getStatusMessage());
		Response response = new Response(exception.getStatusCode(), message, exception.getStatusMessage());
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
}
