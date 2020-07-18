package com.petz.apirest.service;

import java.util.List;

import com.petz.apirest.model.Pet;

public interface PetService {
	
	List<Pet> findByNome(String nome);
	List<Pet> findByEspecie(String especie);
	Pet findById(Long id);

}
