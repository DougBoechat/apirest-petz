package com.petz.apirest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petz.apirest.model.Cliente;
import com.petz.apirest.repository.ClienteRepository;
import com.petz.apirest.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public List<Cliente> findByNome(String nome) {
		nome = "%".concat(nome).concat("%");
		return clienteRepository.findByNome(nome);
	}

	@Override
	public Cliente findById(Long id) {
		Cliente cliente = new Cliente();
		try {
			cliente = clienteRepository.findById(id).get();
		} catch (Exception e) {
			cliente = null;
		}
		
		return cliente;
	}
	
	@Override
	public Cliente findByCpf(String cpf) {
		Cliente cliente = new Cliente();
		try {
			cliente = clienteRepository.findByCpf(cpf);
		} catch (Exception e) {
			cliente = null;
		}
		return cliente;
	}

}
