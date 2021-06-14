package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/articolo", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticoli( @RequestHeader(name = "Authorization", required = false) String token) {
		
		String username = getUsernameFromToken(token);
		
		List<ArticoloDTO> allArtic = null;
		
		if(username!=null) {
			allArtic = articoloServiceImpl.getAllArticoliByUser(username);
		}
		else { //utente anonimo, prendo solo gli articoli pubblicati
			allArtic = articoloServiceImpl.getAllArticoliPubblicati();
		}
		
		if(allArtic==null)
			return (ResponseEntity<?>) ResponseEntity.notFound();
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
