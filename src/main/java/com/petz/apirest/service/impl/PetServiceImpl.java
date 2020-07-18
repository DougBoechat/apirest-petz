package com.petz.apirest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petz.apirest.model.Pet;
import com.petz.apirest.repository.PetRepository;
import com.petz.apirest.service.PetService;

@Service
public class PetServiceImpl implements PetService{
	
	@Autowired
	private PetRepository petRepository;

	@Override
	public List<Pet> findByNome(String nome) {
		nome = "%".concat(nome).concat("%");
		return petRepository.findByNome(nome);
	}

	@Override
	public List<Pet> findByEspecie(String especie) {
		especie = "%".concat(especie).concat("%");
		return petRepository.findByEspecie(especie);
	}

	@Override
	public Pet findById(Long id) {
		Pet pet = new Pet();
		try {
			pet = petRepository.findById(id).get();
		} catch (Exception e) {
			pet = null;
		}
		
		return pet;
	}

}
