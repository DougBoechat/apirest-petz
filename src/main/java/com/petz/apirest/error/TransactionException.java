package com.petz.apirest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionException extends RuntimeException{
	
	private static final long serialVersionUID = -3074538010977279603L;

	public TransactionException(String mensagem) {
		super(mensagem);
	}

}
