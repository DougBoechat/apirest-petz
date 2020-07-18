package com.petz.apirest.resources;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petz.apirest.error.ResourceNotFoundException;
import com.petz.apirest.error.TransactionException;
import com.petz.apirest.model.Cliente;
import com.petz.apirest.repository.ClienteRepository;
import com.petz.apirest.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	@ApiOperation(value = "Retorna lista de clientes")
	public List<Cliente> listClientes(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/cliente/{id}")
	@ApiOperation(value = "Retorna cliente através de seu ID")
	public ResponseEntity<?> listClienteById(@PathVariable(value = "id") Long id) {
		Cliente cliente = clienteService.findById(id);
		if (cliente == null) {
			throw new ResourceNotFoundException("Cliente não encontrado para o ID "+ id + ".");
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/cpf/{cpf}")
	@ApiOperation(value = "Retorna cliente através do seu CPF")
	public ResponseEntity<?> listClienteByCpf(@PathVariable(value = "cpf") String cpf) {
		Cliente cliente = clienteService.findByCpf(cpf);
		if (cliente == null) {
			throw new ResourceNotFoundException("Cliente não encontrado para o CPF "+ cpf + ".");
		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/cliente")
	@ApiOperation(value = "Salva um novo cliente")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		try{
			clienteRepository.save(cliente);
			return new ResponseEntity<>(cliente, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			throw new TransactionException("O CPF informado já está cadastrado no sistema.");
		} catch (TransactionSystemException e) {
			if (e.getRootCause() instanceof ConstraintViolationException) {
		        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getRootCause();
		        ConstraintViolation<?> cv = constraintViolationException.getConstraintViolations().stream().findFirst().get();
		        throw new TransactionException("O campo " + cv.getPropertyPath() + " " + cv.getMessage() + ".");
			} else {
		    	throw new TransactionException("Favor validar as informações.");
		    }
		} catch (Exception e) {
			throw new TransactionException("Favor validar as informações.");
		}
	}
	
	@PutMapping("/cliente/")
	@ApiOperation(value = "Atualiza um cliente")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {
		try{
			clienteRepository.findById(cliente.getId()).get();
			clienteRepository.save(cliente);
			return new ResponseEntity<>(cliente, HttpStatus.CREATED);
		} catch (TransactionSystemException e) {
			if (e.getRootCause() instanceof ConstraintViolationException) {
		        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getRootCause();
		        ConstraintViolation<?> cv = constraintViolationException.getConstraintViolations().stream().findFirst().get();
		        throw new TransactionException("O campo " + cv.getPropertyPath() + " " + cv.getMessage() + ".");
			} else {
		    	throw new TransactionException("Favor validar as informações.");
		    }
		} catch (DataIntegrityViolationException e) {
			throw new TransactionException("O CPF informado já está cadastrado no sistema.");
		} catch (ResourceNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Cliente não encontrado para o ID "+ cliente.getId() + ".");
		} catch (Exception e) {
			throw new TransactionException("Favor validar as informações.");
		}
	}
	
	@DeleteMapping("/cliente/{id}")
	@ApiOperation(value = "Deleta um cliente através de seu ID")
	public ResponseEntity<?> deleteCliente(@PathVariable(value = "id") Long id) {
		try{
			clienteRepository.delete(clienteRepository.findById(id).get());
		} catch (ResourceNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Cliente não encontrado para o ID "+ id + ".");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/clientes/nome/{nome}")
	@ApiOperation(value = "Retorna lista de clientes através de um nome")
	public ResponseEntity<?> listClienteByNome(@PathVariable(value = "nome") String nome) {
		return new ResponseEntity<>(clienteService.findByNome(nome), HttpStatus.OK);
	}

}
