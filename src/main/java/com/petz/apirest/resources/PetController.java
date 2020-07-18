package com.petz.apirest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
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
import com.petz.apirest.model.Pet;
import com.petz.apirest.repository.ClienteRepository;
import com.petz.apirest.repository.PetRepository;
import com.petz.apirest.service.PetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Pets")
@CrossOrigin(origins = "*")
public class PetController {
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/pets")
	@ApiOperation(value = "Retorna lista de pets")
	public List<Pet> listPets(){
		return petRepository.findAll();
	}
	
	@GetMapping("/pet/{id}")
	@ApiOperation(value = "Retorna um pet através de seu ID")
	public ResponseEntity<?> listPetById(@PathVariable(value = "id") Long id) {
		Pet pet = petService.findById(id);
		if (pet == null) {
			throw new ResourceNotFoundException("Pet não encontrado para o ID "+ id + ".");
		}
		return new ResponseEntity<>(pet, HttpStatus.OK);
	}
	
	@GetMapping("/pets/nome/{nome}")
	@ApiOperation(value = "Retorna lista de pets através de um nome")
	public ResponseEntity<?> listPetsByName(@PathVariable(value = "nome") String nome) {
		return new ResponseEntity<>(petService.findByNome(nome), HttpStatus.OK);
	}
	
	@GetMapping("/pets/especie/{especie}")
	@ApiOperation(value = "Retorna lista de pets através de uma espécie")
	public ResponseEntity<?> listPetsByEspecie(@PathVariable(value = "especie") String especie) {
		return new ResponseEntity<>(petService.findByEspecie(especie), HttpStatus.OK);
	}
	
	@GetMapping("/pets/dono/{id}")
	@ApiOperation(value = "Retorna lista de pets através do ID do cliente")
	public ResponseEntity<?> listaPetsByIdDono(@PathVariable(value = "id") Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		List<Pet> pets = new ArrayList<>();
		if (!cliente.isPresent()) {
			throw new ResourceNotFoundException("Não encontrado o dono de ID "+ id + ".");
		} else {
			pets = petRepository.findByDono(cliente.get().getId());
		}
		return new ResponseEntity<>(pets, HttpStatus.OK);
	}
	
	@PostMapping("/pet")
	@ApiOperation(value = "Salva um novo pet")
	public ResponseEntity<?> savePet(@RequestBody Pet pet, BindingResult result) {
		try{
			clienteRepository.findById(pet.getDono().getId()).get();
			petRepository.save(pet);
			return new ResponseEntity<>(pet, HttpStatus.CREATED);
		} catch (ResourceNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Dono não encontrado para o ID "+ pet.getDono().getId() + ".");
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
	
	@PutMapping("/pet/")
	@ApiOperation(value = "Atualiza um pet")
	public ResponseEntity<?> updatePet(@RequestBody Pet pet) {
		try{
			Optional<Pet> petResult = petRepository.findById(pet.getId());
			if (!petResult.isPresent()) {
				throw new ResourceNotFoundException("Não encontrado o pet de ID "+ pet.getId() + ".");
			}
			if (pet.getDono() != null && pet.getDono().getId() != null) {
				Optional<Cliente> clienteResult = clienteRepository.findById(pet.getDono().getId());
				if (!clienteResult.isPresent()) {
					throw new ResourceNotFoundException("Não encontrado o dono de ID "+ pet.getDono().getId() + ".");
				}
			}
			petRepository.save(pet);
			return new ResponseEntity<>(pet, HttpStatus.CREATED);
		} catch (TransactionSystemException e) {
			if (e.getRootCause() instanceof ConstraintViolationException) {
		        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getRootCause();
		        ConstraintViolation<?> cv = constraintViolationException.getConstraintViolations().stream().findFirst().get();
		        throw new TransactionException("O campo " + cv.getPropertyPath() + " " + cv.getMessage() + ".");
			} else {
		    	throw new TransactionException("Favor validar as informações.");
		    }
		}
	}
	
	@DeleteMapping("/pet/{id}")
	@ApiOperation(value = "Deleta um pet através do seu ID")
	public ResponseEntity<?> deletePet(@PathVariable(value = "id") Long id) {
		try{
			petRepository.delete(petRepository.findById(id).get());
		} catch (ResourceNotFoundException | NoSuchElementException e) {
			throw new ResourceNotFoundException("Pet não encontrado para o ID "+ id + ".");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
