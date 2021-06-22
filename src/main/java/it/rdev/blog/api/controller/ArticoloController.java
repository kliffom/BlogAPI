package it.rdev.blog.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.service.impl.ArticoloServiceImpl;

@RestController
@RequestMapping("api")
public class ArticoloController {

	@Autowired
	private ArticoloServiceImpl articoloServiceImpl;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	Logger logger = LoggerFactory.getLogger(ArticoloController.class);
	
	/**
	 * Metodo invocato tramite l'url /api/articolo in modalità GET. In base ad i parametri restituisce un certo valore
	 * @param token - Token di autenticazione dell'utente
	 * @param id 	- ID dell'articolo da ricercare
	 * @param cat	- Categoria dell'articolo da ricercare
	 * @param tag	- Tag dell'articolo da ricercare
	 * @param aut	- Autore dell'articolo da ricercare
	 * @param stato - Stato dell'articolo (BOZZA)
	 * @param search- Parametro di ricerca in titolo, sottotitolo o testo
	 */
	@RequestMapping(value="/articolo", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticoli( @RequestHeader(name = "Authorization", required = false) String token, 
			@Param("id") Long id, @Param("cat") String cat, @Param("tag") String tag, @Param("aut") String aut,
			@Param("stato") String stato, @Param("search") String search) {
		
		String username = getUsernameFromToken(token);
		List<ArticoloDTO> allArtic = null;
		
		logger.info("Parametri ricevuti: [search=" + search + ", id=" + id + ", cat=" + cat + 
				", tag=" + tag + ", aut=" + aut + ", stato=" + stato + "]");
		
		if(search!=null) {
			if(id!=null || cat!=null || tag!=null || aut!=null || stato!=null) { // Sono stati inseriti entrambi i criteri di ricerca, devono essercene solo 1 per volta
				logger.error("Passati entrambi i criteri di ricerca. Solo uno ammesso.");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametri di ricerca non corretti.");
			}
			else if(search.length()<3) {	// La ricerca per contenuto contiene pochi caratteri
				logger.error("Passati meno di 3 caratteri per la ricerca.");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La ricerca deve contenere almeno 3 caratteri.");
			}
			else {
				logger.info("Ricerco articoli contenenti " + search + ".");
				allArtic = articoloServiceImpl.getAllArticoliByContenuto(search);
			}
		}
		
		else if(id!=null || cat!=null || tag!=null || aut!=null || stato!=null) { // Sono stati inseriti alcuni parametri di ricerca
			List<ArticoloDTO> allArticId = new ArrayList<>(); 
			List<ArticoloDTO> allArticCat = new ArrayList<>(); 
			List<ArticoloDTO> allArticTag = new ArrayList<>(); 
			List<ArticoloDTO> allArticAut = new ArrayList<>();
			List<ArticoloDTO> allArticStato = new ArrayList<>();
			
			if(id!=null) { // Eseguo ricerca per id
				logger.info("Ricerco articoli con id " + id + ".");

//				ArticoloDTO artById = articoloServiceImpl.getArticoloById(id);
				ArticoloDTO artById = null;
				try {
					artById = articoloServiceImpl.getArticoloById(id); // il metodo lancia eccezione quando non trova ID, mandando in blocco il sistema
				}
				catch(NoSuchElementException e) {
					logger.info("Nessuna tupla trovata con l'ID specificato.");
				}
				if(artById!=null) // Se l'ID non è presente nel sistema, artById null verrebbe comunque aggiunto all'array non restituendo l'errore 404
					allArticId.add(artById);
				allArtic = allArticId; // assegno su allArtic per poter fare la ricerca comune successivamente
			}
			if(cat!=null) { // Eseguo ricerca per categoria
				logger.info("Ricerco articoli con categoria " + cat + ".");
				allArticCat = articoloServiceImpl.getAllArticoliByCategory(cat);
				allArtic = allArticCat; // assegno anche i successivi su allArtic, nel caso i filtri precedenti fossero null, così da fare il confrondo solo sulle List popolate
			}
			if(tag!=null) { // Eseguo ricerca per tag
				logger.info("Ricerco articoli con tag " + tag + ".");
				allArticTag = articoloServiceImpl.getAllArticoliByTag(tag);
				allArtic = allArticTag;
			}
			if(aut!=null) { // Eseguo ricerca per autore
				logger.info("Ricerco articoli con autore " + aut + ".");
				allArticAut = articoloServiceImpl.getAllArticoliByAuthor(aut);
				allArtic = allArticAut;
			}
			if(stato!=null) { // Eseguo ricerca per stato
				if(!stato.equals("BOZZA")) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stato selezionato non gestito.");
				}
				if(username==null) { // Nessun utente loggato, non si può fare la ricerca per stato
					throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non loggato."); 
				}
				logger.info("Ricerco articoli con stato " + stato + ".");
				allArticStato = articoloServiceImpl.getAllArticoliInBozza(username);
				allArtic = allArticStato;
			}
			
			// cerco gli elementi comuni tra tutti per restituirli
			// retainAll invoca il metodo equals() sugli oggetti contenuti
			// ridefinito equals() in ArticoloDTO in modo che venga controllato solo l'ID di persistenza e non l'ID dell'oggetto
			if(!allArticId.isEmpty())
				allArtic.retainAll(allArticId);
			if(!allArticCat.isEmpty())
				allArtic.retainAll(allArticCat);
			if(!allArticTag.isEmpty())
				allArtic.retainAll(allArticTag);
			if(!allArticAut.isEmpty())
				allArtic.retainAll(allArticAut);
			if(!allArticStato.isEmpty())
				allArtic.retainAll(allArticStato);
		}

		else {		// Se non ci sono parametri di ricerca, eseguirà le query di prelievo di tutti gli articoli
			if(username!=null) {
				allArtic = articoloServiceImpl.getAllArticoliByUser(username);
			}
			else { //utente anonimo, prendo solo gli articoli pubblicati
				allArtic = articoloServiceImpl.getAllArticoliPubblicati();
			}
		}
		if(allArtic==null || allArtic.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun articolo trovato.");
		else
			return ResponseEntity.ok(allArtic);
	}
	
	/**
	 * Metodo invocato tramite l'url /api/articolo in modalità POST. Consente l'aggiunta  di un articolo in bozza ad un utente loggato.
	 * @param token 	- Token di autenticazione dell'utente
	 * @param articolo	- Articolo in formato JSON da aggiungere
	 */
	@RequestMapping(value="/articolo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addArticolo( @RequestHeader(name = "Authorization", required = true) String token, 
			@RequestBody ArticoloDTO articolo) throws Exception {
		
		Long id = getUserIdFromToken(token);
		String username = getUsernameFromToken(token);
		
		if(username==null) { //utente non loggato
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non loggato e non autorzzato.");
		}
		
		logger.info("Invocazione aggiunta bozza articolo:");
		logger.info(articolo.toString());
		logger.info("ID Utente: " + id);
		
		if(articolo.getTitolo()==null ||
				articolo.getTesto()==null) { // Parametri in input non corretti
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametri non conformi.");
		}
		
		if(articoloServiceImpl.save(articolo, username) instanceof Articolo) {
			logger.info("Aggiunta eseguita correttamente.");
		}
		else 
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante l'aggiunta dell'articolo.");
	}
	
	/**
	 * Metodo invocato tramite l'url /api/articolo<:id> in modalità PUT.
	 * Consente la modifica e l'aggiornamento di un articolo inserito precedentemente.
	 * @param id 		- ID dell'articolo da modificare
	 * @param token		- Token di autenticazione dell'utente
	 * @param articolo	- Articolo in formato JSON contenente i campi da modificare
	 */
	@RequestMapping(value="/articolo/{id:\\d+}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateArticolo(@PathVariable final Long id, 
			@RequestHeader(name = "Authorization", required = true) String token, 
			@RequestBody ArticoloDTO articolo) throws Exception {
		
		String username = getUsernameFromToken(token);
		if(username!=null) { // Utente loggato
			
			ArticoloDTO artDto = null;
			try {
				artDto = articoloServiceImpl.getArticoloById(id);
			} catch(NoSuchElementException e) { // Se non trova l'elemento va in eccezione, la gestisco per lanciare l'errore 404  
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non presente nel sistema.");
			}
			
			if(artDto == null) { // Articolo non trovato
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non presente nel sistema.");
			}
			
			logger.info("Articolo prelevato: " + artDto.toString());
			logger.info("Utente articolo prelevato: " + artDto.getUser().toString());
			
			if(!artDto.getUser().getUsername().equals(getUsernameFromToken(token))) { // Utente loggato differente dall'autore
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Utente loggato differente da autore articolo.");
			}
			
			// Controllo se l'articolo è in stato di bozza, altrimenti ne impedisco la modifica
			if(!artDto.isBozza()) {
				throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Articolo pubblicato, non più modificabile.");
			}
			
			// Controllo i parametri inseriti nel JSON per vedere se sono null o da aggiornare
			
			if(articolo.getTitolo()!=null)  // Nuovo titolo da aggiornare
				artDto.setTitolo(articolo.getTitolo());
			
			if(articolo.getSottotitolo()!=null)
				artDto.setSottotitolo(articolo.getSottotitolo());
			
			if(articolo.getTesto()!=null)
				artDto.setTesto(articolo.getTesto());
			
			if(articolo.getCategoria()!=null)
				artDto.setCategoria(articolo.getCategoria());
			
			if(articolo.getTags()!=null)
				artDto.setTags(articolo.getTags());
			
			if((Boolean) articolo.isBozza()!=null)
				artDto.setBozza(articolo.isBozza());
			
			// Nuovo articolo aggiornato, update su DB
			if(articoloServiceImpl.update(artDto, username) instanceof Articolo) {
				logger.info("Aggiornamento eseguito correttamente.");
			}
			
		}
		else 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non loggato."); // Nessun utente loggato
	}
	
	/**
	 * Metodo invocato tramite l'url /api/articolo/<:id> in modalità DELETE.
	 * Elimina un articolo in base all'id ricevuto.
	 * @param id		- ID dell'articolo da eliminare
	 * @param token		- Token di autenticazione dell'utente
	 */
	@RequestMapping(value="/articolo/{id:\\d+}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteArticolo(@PathVariable final Long id, 
			@RequestHeader(name = "Authorization", required = true) String token) throws Exception {
		
		String username = getUsernameFromToken(token);
		if(username!=null) { // Utente loggato
			
			// Controllo se l'articolo è presente nel sistema
			ArticoloDTO artDto = null;
			try {
				artDto = articoloServiceImpl.getArticoloById(id);
			} catch(NoSuchElementException e) { // Se non trova l'elemento va in eccezione, la gestisco per lanciare l'errore 404  
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non presente nel sistema.");
			}
			try {
				artDto = articoloServiceImpl.getArticoloById(id);
			} catch(NoSuchElementException e) { // Se non trova l'elemento va in eccezione, la gestisco per lanciare l'errore 404  
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non presente nel sistema.");
			}
			
			if(!artDto.getUser().getUsername().equals(getUsernameFromToken(token))) { // Utente loggato differente dall'autore
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Utente loggato differente da autore articolo.");
			}
			
			// A questo punto è possibile invocare il metodo di eliminazione dell'articolo
			
			articoloServiceImpl.delete(artDto.getId());
			
			
		}
		else
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non loggato."); // Nessun utente loggato
	}

	/**
	 * Metodo invocato tramite l'url /api/articolo/<:id> in modalità GET. Restituisce un articolo in base all'id passato
	 * @param id	- ID dell'articolo da ricercare
	 * @param token - Token di autenticazione dell'utente
	 */
	@RequestMapping(value="/articolo/{id:\\d+}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticoloById(@PathVariable final Long id, @RequestHeader(name = "Authorization", required = false) String token) {
		
		String username = getUsernameFromToken(token);
		
		
		
		
		
		ArticoloDTO artic = null;
		try {
			artic = articoloServiceImpl.getArticoloById(id);
		} catch(NoSuchElementException e) { // Se non trova l'elemento va in eccezione, la gestisco per lanciare l'errore 404  
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articolo non presente nel sistema.");
		}
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
	
	/**
	 * Funzione di supporto, consente di ottenere lo username dell'utente dal token di autenticazione
	 * @param token	- Token di autenticazione dell'utente
	 * @return String contenente lo username
	 */
	private String getUsernameFromToken(String token) {
		
		String username = null;
		
		if(token!=null && token.startsWith("Bearer")) {
			token = token.replaceAll("Bearer ", "");
			username = jwtUtil.getUsernameFromToken(token);
		}
		
		return username;
	}
	
	/**
	 * Funzione di supporto, consente di ottenere l'id dell'utente dal token di autenticazione
	 * @param token	- Token di autenticazione dell'utente
	 * @return Long contenente l'ID
	 */
	private Long getUserIdFromToken(String token) {
		
		Long id=null;
		
		if(token!=null && token.startsWith("Bearer")) {
			token = token.replaceAll("Bearer ", "");
			id = jwtUtil.getUserIdFromToken(token);
		}
		
		return id;
	}
}
