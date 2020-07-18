package com.petz.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petz.apirest.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT c FROM Cliente c where c.cpf like :cpf")
	public Cliente findByCpf(String cpf);
	
	@Query("SELECT c FROM Cliente c where c.nome like :nome")
	public List<Cliente> findByNome(String nome);

}
