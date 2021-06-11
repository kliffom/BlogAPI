package it.rdev.blog.api.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Categoria;

@Repository
public interface CategoriaDao extends CrudRepository<Categoria, String> {

	/**
	 * Restituisce un oggetto categoria in base alla descrizione che la caratterizza
	 * @param descrizione - nome della categoria
	 * @return {@link Categoria} oggetto con la categoria scelta
	 */
	Categoria findByDescrizione(String descrizione);
	
	
}
