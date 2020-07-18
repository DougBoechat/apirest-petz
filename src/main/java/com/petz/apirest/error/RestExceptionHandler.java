package com.petz.apirest.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rfn){
		ErrorTemplateDetails rfnDetails = ErrorTemplateDetails.newBuilder().timestamp(new Date().getTime()).status(HttpStatus.NOT_FOUND.value())
		.title("Recurso não encontrado.").detail(rfn.getMessage()).developerMessage(rfn.getClass().getName()).build();
		return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<?> handlerTransactionException(TransactionException rfn){
		ErrorTemplateDetails rfnDetails = ErrorTemplateDetails.newBuilder().timestamp(new Date().getTime()).status(HttpStatus.BAD_REQUEST.value())
		.title("Solicitação incorreta.").detail(rfn.getMessage()).developerMessage(rfn.getClass().getName()).build();
		return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
	}
}
