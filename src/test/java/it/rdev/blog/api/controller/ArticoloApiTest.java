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
	 * Testing GET /api/articolo con non tabella vuota con login. 
	 * La chiamata dovrebbe restituire anche articoli in bozza dell'utente attuale.
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonEmptyArticleListLoginTest")
	void getNonEmptyArticleListLoginTest() {
		
		client.get().uri("/api/articolo")
		.header("Authorization", "Bearer " + login(art1.getUser().getUsername(), "pangaro"))
		.exchange().expectStatus().isOk() // Atteso codice 200 quando la tabella Articolo restituisce qualcosa
		.expectBody().jsonPath("$[1].bozza").isEqualTo(true); //Controllo che l'articolo restituito sia con bozza = true in quanto il secondo articolo aggiunto in TestDbInit è una bozza
		
	}
	
	/**
	 * Testing GET /api/articolo/id con la tupla non esistente nel db 
	 */
	@Test
	@DisplayName("ArticoloApiTest - getNonExistingArticleByIdTest")
	void getNonExistingArticleByIdTest() {
		
		client.get().uri("/api/articolo/1")
		.exchange().expectStatus().isNotFound();
	}
	
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
