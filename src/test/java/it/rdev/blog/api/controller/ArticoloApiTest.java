package it.rdev.blog.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.TestDbInit;
import it.rdev.blog.api.dao.ArticoloDao;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("<= ArticoloApiTest =>")
@AutoConfigureWebTestClient(timeout = "69696969") // Aumento il timeout poiché il test terminava mentre ero in debug mode durante l'analisi del codice
public class ArticoloApiTest extends TestDbInit{
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private ArticoloDao artDao;
	
	@BeforeEach
	public void setup() {
		
	}
	
	private ArticoloApiTest() { }
	
	/******** SEZIONE GET DEGLI ARTICOLI ********/
	
	/**
	 * Testing GET /api/articolo con tabella vuota
	 */
	@Test
	@DisplayName("ArticoloApiTest - getEmptyArticleListTest")
	void getEmptyArticleListTest() {
		
		artDao.deleteAll(); // Elimino tutti gli articoli per testare quando la tabella è vuota
		
		client.get().uri("/api/articolo")
		.exchange().expectStatus().isNotFound(); // Atteso errore 404 quando la tabella Articolo è vuota
		
	}
	
	/**
	 * Testing GET /api/articolo con non tabella vuota senza login. 
	 * La chiamata dovrebbe restituire solo articoli non in bozza
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginTest")
	void getNonEmptyArticleListNoLoginTest() {
		
		client.get().uri("/api/articolo")
		.exchange().expectStatus().isOk() // Atteso codice 200 quando la tabella Articolo restituisce qualcosa
		.expectBody().jsonPath("$[0].bozza").isEqualTo(false); //Controllo che l'articolo restituito sia con bozza = false
		
	}
	
	/**
	 * Testing GET /api/articolo?search='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per titolo, sottotitolo o testo.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginSearchCriteriaTest")
	void getNonEmptyArticleListNoLoginSearchCriteriaTest() {
		
		client.get().uri("/api/articolo?search=icolo")
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando la tabella Articolo restituisce qualcosa

		client.get().uri("/api/articolo?search=nonpresente")
		.exchange().expectStatus().isNotFound(); // Atteso codice 404 quando la tabella Articolo non restituisce niente
	}
	
	/**
	 * Testing GET /api/articolo?id='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per id.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginIdCriteriaTest")
	void getNonEmptyArticleListNoLoginIdCriteriaTest() {
		
		client.get().uri("/api/articolo?id=2")
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando la tabella Articolo restituisce qualcosa

		client.get().uri("/api/articolo?id=5")
		.exchange().expectStatus().isNotFound(); // Atteso codice 404 quando la tabella Articolo non restituisce niente
	}
	
	/**
	 * Testing GET /api/articolo?cat='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per categoria.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginCategoryCriteriaTest")
	void getNonEmptyArticleListNoLoginCategoryCriteriaTest() {
		
		client.get().uri("/api/articolo?cat=Tecnologia")
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando la tabella Articolo restituisce qualcosa

		client.get().uri("/api/articolo?cat=CategoriaNonEsistente")
		.exchange().expectStatus().isNotFound(); // Atteso codice 404 quando la tabella Articolo non restituisce niente
	}
	
	/**
	 * Testing GET /api/articolo?tag='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per tag.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginTagCriteriaTest")
	void getNonEmptyArticleListNoLoginTagCriteriaTest() {
		
		client.get().uri("/api/articolo?tag=Programmazione")
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando la tabella Articolo restituisce qualcosa

		client.get().uri("/api/articolo?tag=TagNonEsistente")
		.exchange().expectStatus().isNotFound(); // Atteso codice 404 quando la tabella Articolo non restituisce niente
	}
	
	/**
	 * Testing GET /api/articolo?aut='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per autore.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginAuthorCriteriaTest")
	void getNonEmptyArticleListNoLoginAuthorCriteriaTest() {
		
		client.get().uri("/api/articolo?aut=pangaro")
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando la tabella Articolo restituisce qualcosa

		client.get().uri("/api/articolo?aut=AutoreNonEsistente")
		.exchange().expectStatus().isNotFound(); // Atteso codice 404 quando la tabella Articolo non restituisce niente
	}

	/**
	 * Testing GET /api/articolo?stato='' con non tabella vuota senza login 
	 * utilizzando un criterio di ricerca per bozza per gli utenti loggati.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListNoLoginBozzaCriteriaTest")
	void getNonEmptyArticleListNoLoginBozzaCriteriaTest() {
		
		client.get().uri("/api/articolo?stato=BOZZA")
		.exchange().expectStatus().isUnauthorized(); // Atteso codice 401 quando l'utente non è loggato e vuole vedere gli articoli in bozza

	}
	
	/**
	 * Testing GET /api/articolo?stato='' con non tabella vuota con login 
	 * utilizzando un criterio di ricerca per bozza per gli utenti loggati.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListLoginBozzaCriteriaTest")
	void getNonEmptyArticleListLoginBozzaCriteriaTest() {
		
		client.get().uri("/api/articolo?stato=BOZZA")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))
		.exchange().expectStatus().isOk(); // Atteso codice 200 quando l'utente è loggato e vuole vedere gli articoli in bozza inserendo BOZZA

		client.get().uri("/api/articolo?stato=ALTRO")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))
		.exchange().expectStatus().isBadRequest(); // Atteso codice 400 quando l'utente è loggato e vuole vedere gli articoli in bozza non inserendo BOZZA
	}

	
	/**
	 * Testing GET /api/articolo con tabella non vuota con login. 
	 * La chiamata dovrebbe restituire anche articoli in bozza dell'utente attuale.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListLoginTest")
	void getNonEmptyArticleListLoginTest() {
		
//		client.get().uri("/api/articolo")
//		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))
//		.exchange().expectStatus().isOk() // Atteso codice 200 quando la tabella Articolo restituisce qualcosa
//		.expectBody().jsonPath("$[1].bozza").isEqualTo(true); //Controllo che l'articolo restituito sia con bozza = true in quanto il secondo articolo aggiunto in TestDbInit è una bozza
		
		String s = new String(
				client.get().uri("/api/articolo")
				.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))
				.exchange().expectBody().returnResult().getResponseBody()
				);
		
		System.out.println("JSON in /api/articolo:");
		System.out.println(s);
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla non esistente nel db 
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonExistingArticleByIdTest")
	void getNonExistingArticleByIdTest() {
		
		client.get().uri("/api/articolo/10")
		.exchange().expectStatus().isNotFound();
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla esistente ma in stato di bozza 
	 */
	@Test
	@DisplayName("ArticoloApiTest - getExistingArticleBozzaNotLoggedByIdTest")
	void getExistingArticleBozzaNotLoggedByIdTest() {
		
		client.get().uri("/api/articolo/1")
		.exchange().expectStatus().isNotFound();
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla esistente ma in stato di bozza e di un utente diverso dall'autore
	 */
	@Test
	@DisplayName("ArticoloApiTest - getExistingArticleBozzaLoggedNonAuthorByIdTest")
	void getExistingArticleBozzaLoggedNonAuthorByIdTest() {
		
		client.get().uri("/api/articolo/1")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro")) // Utente non autore dell'articolo con ID 1
		.exchange().expectStatus().isNotFound();
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla esistente ma in stato di bozza
	 */
	@Test
	@DisplayName("ArticoloApiTest - getExistingArticleBozzaLoggedAuthorByIdTest")
	void getExistingArticleBozzaLoggedAuthorByIdTest() {
		
		client.get().uri("/api/articolo/3")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro")) // Utente non autore dell'articolo con ID 1
		.exchange().expectStatus().isOk()
		.expectBody().jsonPath("$.titolo").isEqualTo(art3.getTitolo());
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla esistente nel db 
	 */
	@Test
	@DisplayName("ArticoloApiTest - getExistingArticleByIdTest")
	void getExistingArticleByIdTest() {
		
		client.get().uri("/api/articolo/2")
		.exchange().expectStatus().isOk()
		.expectBody().jsonPath("$.titolo").isEqualTo(art2.getTitolo());
	}
	
	/******* SEZIONE ACCESSI AUTORIZZATI PER AGGIUNTA, MODIFICA ED ELIMINAZIONE ********/
	
	/**
	 * Testing POST /api/articolo senza login
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - postAddArticleNonLoggedTest\"")
	void postAddArticleNonLoggedTest() {
		
		client.post().uri("/api/articolo")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"titolo\":\"" + art1.getTitolo() + "\", "
				+ "\"testo\":\"" + art1.getTesto() + "\", "
				+ "\"categoria\":{\"descrizione\":\"" + art1.getCategoria().getDescrizione() + "\"}, "
				+ "\"tags\":[{\"nome\":\"" + art1.getTags().get(0).getNome() + "\"}] "
				+ " }")
		.exchange().expectStatus().isUnauthorized();
	}
	
	/**
	 * Testing POST /api/articolo con login
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - postAddArticleLoggedTest\"")
	void postAddArticleLoggedTest() {
		
		client.post().uri("/api/articolo")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"titolo\":\"" + art1.getTitolo() + "\", "
				+ "\"testo\":\"" + art1.getTesto() + "\", "
				+ "\"categoria\":{\"descrizione\":\"" + art1.getCategoria().getDescrizione() + "\"}, "
				+ "\"tags\":[{\"nome\":\"" + art1.getTags().get(0).getNome() + "\"}] "
				+ " }") 									// Body JSON dell'articolo da aggiungere
		.header("Authorization", "Bearer " + login(art1.getUser().getUsername(), "pangaro"))			// Header contenente il token ricevuto dal login
		.exchange().expectStatus().isNoContent();
		
	}
	
	/**
	 * Testing POST /api/articolo con login con parametri necessari mancanti
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - postAddArticleLoggedBadParamTest\"")
	void postAddArticleLoggedBadParamTest() {
		
		client.post().uri("/api/articolo")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"titolo\":\"" + art1.getTitolo() + "\", "
//				+ "\"testo\":\"" + art1.getTesto() + "\", "			// Commento la sezione del testo così da avere una Bad Request
				+ "\"categoria\":{\"descrizione\":\"" + art1.getCategoria().getDescrizione() + "\"}, "
				+ "\"tags\":[{\"nome\":\"" + art1.getTags().get(0).getNome() + "\"}] "
				+ " }") 									// Body JSON dell'articolo da aggiungere
		.header("Authorization", "Bearer " + login(art1.getUser().getUsername(), "pangaro"))			// Header contenente il token ricevuto dal login
		.exchange().expectStatus().isBadRequest();
	}
	
	/**
	 * Testing PUT /api/articolo/id senza login 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - putEditArticoloNoLoggedTest\"")
	void putEditArticoloNoLoggedTest() {
		
		client.put().uri("/api/articolo/1")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\":\"NuovoTitoloModificato\", "
					+ "\"testo\":\"Nuovo testo modificato\" "
					+ " }") 									// Body JSON dell'articolo da modificare
			.exchange().expectStatus().isUnauthorized();
	}
	
	/**
	 * Testing PUT /api/articolo/id con login ma utente sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - putEditArticoloLoggedWrongUserTest\"")
	void putEditArticoloLoggedWrongUserTest() {
		
		client.put().uri("/api/articolo/1")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\":\"NuovoTitoloModificato\", "
					+ "\"testo\":\"Nuovo testo modificato\" "
					+ " }") 									// Body JSON dell'articolo da modificare
			.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è differente da quello dell'articolo
			.exchange().expectStatus().isForbidden();
	}
	
	/**
	 * Testing PUT /api/articolo/id con login ma ID sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - putEditArticoloLoggedWrongIdTest\"")
	void putEditArticoloLoggedWrongIdTest() {
		
		client.put().uri("/api/articolo/100")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\":\"NuovoTitoloModificato\", "
					+ "\"testo\":\"Nuovo testo modificato\" "
					+ " }") 									// Body JSON dell'articolo da modificare
			.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è differente da quello dell'articolo
			.exchange().expectStatus().isNotFound();
	}
	
	/**
	 * Testing PUT /api/articolo/id con login ma ID sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - putEditArticoloLoggedNonBozzaTest\"")
	void putEditArticoloLoggedNonBozzaTest() {
		
		client.put().uri("/api/articolo/2") 		// L'articolo con ID 2 non è in bozza
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\":\"NuovoTitoloModificato\", "
					+ "\"testo\":\"Nuovo testo modificato\" "
					+ " }") 									// Body JSON dell'articolo da modificare
			.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è uguale a quello dell'autore
			.exchange().expectStatus().is4xxClientError(); //WebTestClient non gestisce HTTP Code 418 I'm a Teapot
	}
	
	/**
	 * Testing PUT /api/articolo/id con login ma ID sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - putEditArticoloLoggedTest\"")
	void putEditArticoloLoggedTest() {
		
		client.put().uri("/api/articolo/3") 		// L'articolo con ID 3 è in bozza
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\":\"NuovoTitoloModificato\", "
					+ "\"testo\":\"Nuovo testo modificato\" "
					+ " }") 									// Body JSON dell'articolo da modificare
			.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è uguale a quello dell'autore
			.exchange().expectStatus().isNoContent();
	}
	
	/**
	 * Testing DELETE /api/articolo/id senza login 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - deleteArticoloNonLoggedTest\"")
	void deleteArticoloNonLoggedTest() {
		
		client.delete().uri("/api/articolo/1")
		.exchange().expectStatus().isUnauthorized();
	}
	
	/**
	 * Testing DELETE /api/articolo/id con login ma utente sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - deleteArticoloLoggedWrongUserTest\"")
	void deleteArticoloLoggedWrongUserTest() {
		
		client.delete().uri("/api/articolo/1")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è uguale a quello dell'autore
		.exchange().expectStatus().isForbidden();
	}
	
	/**
	 * Testing DELETE /api/articolo/id con login ma id sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - deleteArticoloLoggedWrongIdTest\"")
	void deleteArticoloLoggedWrongIdTest() {
		
		client.delete().uri("/api/articolo/10")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è uguale a quello dell'autore
		.exchange().expectStatus().isNotFound();
	}
	
	/**
	 * Testing DELETE /api/articolo/id con login ma utente sbagliato 
	 */
	@Test
	@DisplayName("\"ArticoloApiTest - deleteArticoloLoggedTest\"")
	void deleteArticoloLoggedTest() {
		
		client.delete().uri("/api/articolo/3")
		.header("Authorization", "Bearer " + login(user2.getUsername(), "pangaro"))	// L'utente è uguale a quello dell'autore
		.exchange().expectStatus().isNoContent();
	}
	
	
	
	/**
	 * Metodo di utility per il login dell'utente prima di invocare le chiamate protette
	 * utilizzato invece di riscrivere il codice in ogni test che utilizza il login.
	 * @param user - username per il login
	 * @param psw  - password per il login
	 * @return String token contenente il token di autenticazione
	 */
	private String login(String user, String psw) {
		
		// Eseguo il login sul sistema e prendo la risposta dal server che dovrebbe contenere il token di autenticazione
		byte[] response = client.post().uri("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"username\": \"" + user2.getUsername() + "\", \"password\": \"pangaro\" }") // la password la passo manualmente perché user1 la contiene cifrata
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.token").exists()
				.returnResult().getResponseBodyContent();
		
		String token = null;
		String textResp = new String(response);
		
		int lastPos = textResp.lastIndexOf("\"");
		if(lastPos >= 0) {
			textResp = textResp.substring(0, lastPos);
			lastPos = textResp.lastIndexOf("\"");
			if(lastPos >= 0) {
				token = textResp.substring(lastPos + 1);
				log.info("TOKEN ----> " + token);
			}
		}
		
		return token;
	}
	
}
