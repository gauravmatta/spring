package com.springimplant.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springimplant.exceptions.EmptyInputException;

@ControllerAdvice
public class MyControllerAdvice {
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){
		return new ResponseEntity<>("Input field is empty",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Pass Number Only")
	public ResponseEntity<String> handleNullInput(EmptyInputException emptyInputException){
		return new ResponseEntity<>("Input field is Null",HttpStatus.BAD_REQUEST);
	}
	
}
