package com.rafaelcortez.bookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rafaelcortez.bookstore.domain.Categoria;
import com.rafaelcortez.bookstore.dto.CategoriaDTO;
import com.rafaelcortez.bookstore.repositories.CategoriaRepository;
import com.rafaelcortez.bookstore.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDTO) {
		Categoria obj = findById(id);
		obj.setNome(objDTO.getNome());
		obj.setDescricao(objDTO.getDescricao());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.rafaelcortez.bookstore.services.exceptions.DataIntegrityViolationException
			("Categoria não pode ser deletada! Possui livros associados");
		}
	}
}
