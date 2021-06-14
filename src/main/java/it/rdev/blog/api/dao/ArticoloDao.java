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
	 * @return List<{@link Articolo}> - Lista di articoli scritti dall'autore
	 */
	List<Articolo> findByUser(Long id_user);
	
	/**
	 * Restituisce una lista di articoli che sono stati pubblicati (flag bozza=false)
	 * @return List<{@link Articolo} - Lista di articoli pubblicati
	 */
	@Query("SELECT a FROM Articolo a WHERE a.bozza = FALSE")
	List<Articolo> findAllPubblicati();
	
	/**
	 * Restituisce una lista di articoli. Passando in input uno username, 
	 * la query andrà a restituire tutti gli articoli pubblicati 
	 * e quelli in stato di bozza da parte dell'utente 'username'.
	 * @param username - username dell'autore degli articoli
	 * @return List<{@link Articolo}> - Lista di articoli pubblicati e in bozza di username
	 */
	@Query("SELECT a FROM Articolo a WHERE a.id NOT IN ("
			+ "SELECT a1.id FROM Articolo a1 "
			+ "JOIN a1.user u "
			+ "WHERE "
			+ "u.username!=:username AND "
			+ "a1.bozza = TRUE"
			+ ")")
	List<Articolo> findAllByUser(@Param("username") String username);
	
	/**
	 * Restituisce una lista di articoli. 
	 * Gli articoli contengono in titolo, sottotitolo o testo il valore ricevuto in input
	 * @param searchValue - Valore da ricercare in titolo, sottotitolo o testo
	 * @return List<{@link Articolo}> - Lista di articoli contenenti il valore ricercato
	 */
	@Query("SELECT a FROM Articolo a "
			+ "WHERE a.titolo LIKE CONCAT('%',:searchValue,'%') "
			+ "OR a.sottotitolo LIKE CONCAT('%',:searchValue,'%') "
			+ "OR a.testo LIKE CONCAT('%',:searchValue,'%')")
	List<Articolo> findAllByContenuto(@Param("searchValue") String searchValue);
	
}
