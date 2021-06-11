package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.dao.entity.Categoria;

public interface CategoriaService {

	List<Categoria> getAllCategorie();
	
	Categoria getCategoriaByDescrizione(String descrizione);
}
