package it.rdev.blog.api.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;

@Repository
public interface TagDao extends CrudRepository<Tag, String>{

	/**
	 * Restituisce un oggetto tag caratterizzato dal nome passato
	 * @param nome - indicativo PK del tag
	 * @return {@link Tag} corrispondente al nome
	 */
	Tag findByNome(String nome);
	
	
	
}
