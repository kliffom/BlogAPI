package it.rdev.blog.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.service.impl.ArticoloServiceImpl;

@RestController
@RequestMapping("api")
public class ArticoloController {

	@Autowired
	private ArticoloServiceImpl articoloServiceImpl;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	Logger logger = LoggerFactory.getLogger(ArticoloController.class);
	
	@RequestMapping(value="/articolo", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticoli( @RequestHeader(name = "Authorization", required = false) String token, 
			@Param("id") Long id, @Param("cat") String category, @Param("tag") String tag, @Param("aut") String autore,
			@Param("search") String search) {
		
		String username = getUsernameFromToken(token);
		List<ArticoloDTO> allArtic = null;
		
		if(search!=null) {
			if(id!=null || category!=null || tag!=null || autore!=null) { // Sono stati inseriti entrambi i criteri di ricerca, devono essercene solo 1 per volta
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametri di ricerca non corretti.");
			}
			else if(search.length()<2) {	// La ricerca per contenuto contiene pochi caratteri
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La ricerca deve contenere almeno 3 caratteri.");
			}
			else {
				allArtic = articoloServiceImpl.getAllArticoliByContenuto(search);
			}
		}
		
		else if(id!=null || category!=null || tag!=null || autore!=null) { // Sono stati inseriti alcuni parametri di ricerca
			// Invoca qui la ricerca su questi parametri
		}

		else {		// Se non ci sono parametri di ricerca, eseguirà le query di prelievo di tutti gli articoli
			if(username!=null) {
				allArtic = articoloServiceImpl.getAllArticoliByUser(username);
			}
			else { //utente anonimo, prendo solo gli articoli pubblicati
				allArtic = articoloServiceImpl.getAllArticoliPubblicati();
			}
		}
		if(allArtic==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun articolo trovato.");
		else
			return ResponseEntity.ok(allArtic);
	}
	

	@RequestMapping(value="/articolo/{id:\\d+}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticoloById(@PathVariable final Long id, @RequestHeader(name = "Authorization", required = false) String token) {
		
		String username = getUsernameFromToken(token);
		
		//TODO correggere problema di get solo bozza quando loggato
		
		ArticoloDTO artic = articoloServiceImpl.getArticoloById(id);
		if(artic==null)		// Articolo non trovato
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non trovato.");
		else {				// Articolo trovato
			if(username==null) 		// Utente anonimo
				if(!artic.isBozza())	// Articolo pubblicato
					return ResponseEntity.ok(artic);	// Restituisco articolo
				else					// Articolo in bozza
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non ancora pubblicato.");	// Non restituisco articolo
			else {					// Utente loggato
				if(artic.isBozza()) {	// Se l'articolo è in bozza
					if(artic.getUser().getUsername().equals(username)) { // Ed è scritto dall'utente loggato
						return ResponseEntity.ok(artic);	// Restituisci articolo
					}
					else { // Se non è scritto dall'utente loggato
						throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non sei l'utente che ha scritto la bozza.");
					}
				}
				else {	// Se l'articolo è pubblicato, può essere visualizzato da chiunque
					return ResponseEntity.ok(artic);	// Restituisco articolo
				}
			}
		}
	}
	
	private String getUsernameFromToken(String token) {
		
		String username = null;
		
		if(token!=null && token.startsWith("Bearer")) {
			token = token.replaceAll("Bearer ", "");
			username = jwtUtil.getUsernameFromToken(token);
		}
		
		return username;
	}
}
