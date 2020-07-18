package com.petz.apirest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -7383386860023479315L;

	public ResourceNotFoundException(String mensagem) {
		super(mensagem);
	}

}