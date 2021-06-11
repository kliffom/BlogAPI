package it.rdev.blog.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
	List<Articolo> findByUser(Long id_user);
	
	@Query("SELECT a FROM Articolo a WHERE a.bozza = FALSE")
	List<Articolo> findAllPubblicati();
	
	@Query("SELECT a FROM Articolo a WHERE a.id NOT IN ("
			+ "SELECT a1.id FROM Articolo a1 "
			+ "JOIN a1.user u "
			+ "WHERE "
			+ "u.username!=:username AND "
			+ "a1.bozza = TRUE"
			+ ")")
	List<Articolo> findAllByUser(@Param("username") String username);
	
	
	
}
