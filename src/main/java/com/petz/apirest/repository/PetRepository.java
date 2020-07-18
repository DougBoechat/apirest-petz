package com.petz.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petz.apirest.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{
	
	@Query("SELECT p FROM Pet p where p.nome like :nome")
	public List<Pet> findByNome(String nome);
	
	@Query("SELECT p FROM Pet p where p.especie like :especie")
	public List<Pet> findByEspecie(String especie);
	
	@Query("SELECT p FROM Pet p JOIN FETCH p.dono d where d.id like :idDono")
	public List<Pet> findByDono(Long idDono);
	
}
