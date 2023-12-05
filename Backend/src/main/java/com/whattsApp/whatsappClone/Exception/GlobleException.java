package com.whattsApp.whatsappClone.Exception;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobleException {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e, WebRequest req){
		ErrorDetail err= new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException ue, WebRequest req){
		ErrorDetail err= new ErrorDetail(ue.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException ue, WebRequest req){
		ErrorDetail err= new ErrorDetail(ue.getMessage(), req.getDescription(false), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetail> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me, WebRequest req){
		String error = me.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetail err = new ErrorDetail("Validation Error", error, LocalDateTime.now());

		
		return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetail> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest req){
		ErrorDetail error= new ErrorDetail("EndPoint not found", ex.getMessage(), LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(error, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req){
		
		ErrorDetail err= new ErrorDetail( e.getMessage(),req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	/*@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    // Log details for debugging
	    log.error("HTTP Media Type Not Acceptable: {}", ex.getMessage());

	    // Customize your response or return a generic error response
	    return ResponseEntity
	            .status(HttpStatus.NOT_ACCEPTABLE)
	            .body("The requested media type is not supported");
	}
*/
	

}
