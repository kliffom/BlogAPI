package it.rdev.blog.api.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.Articolo;

@Repository
public interface ArticoloDao extends CrudRepository<Articolo, Long>{

	//findById esiste già di default
	
	/**
	 * Restituisce una lista di articoli in base all'ID di un autore ricevuto
	 * @param Long id_autore - ID dell'autore 
	 * @return List<{@link Articolo} - Lista di articoli scritti dall'autore
	 */
	List<Articolo> findByAutore(Long id_autore);
	
	
}
