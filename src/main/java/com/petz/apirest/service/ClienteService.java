package com.petz.apirest.service;

import java.util.List;

import com.petz.apirest.model.Cliente;

public interface ClienteService {
	
	Cliente findById(Long id);
	Cliente findByCpf(String cpf);
	List<Cliente> findByNome(String nome);

}
