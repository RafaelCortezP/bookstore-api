package com.rafaelcortez.bookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelcortez.bookstore.domain.Categoria;
import com.rafaelcortez.bookstore.domain.Livro;
import com.rafaelcortez.bookstore.repositories.LivroRepository;
import com.rafaelcortez.bookstore.services.exceptions.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Integer id) {
		Optional<Livro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não encontrado! Id: " + id + ", Tipo: " + Livro.class.getName()));
	}
	
	public List<Livro> findByCategoria(Integer idCategoria){
		categoriaService.findById(idCategoria);
		return repository.findByCategoria(idCategoria);
	}
	
	public Livro insert(Integer idCategoria, Livro obj) {
		obj.setId(null);
		Categoria cat = categoriaService.findById(idCategoria);
		obj.setCategoria(cat);
		return repository.save(obj);
	}

	public Livro update(Integer id, Livro obj) {
		Livro newObj = findById(id);
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Livro newObj, Livro obj) {
		newObj.setTitulo(obj.getTitulo());
		newObj.setNomeAutor(obj.getNomeAutor());
		newObj.setTexto(obj.getTexto());
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}
}
