package it.rdev.blog.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.TestDbInit;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.entity.Categoria;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("<= CategoriaApiTest =>")
public class CategoriaApiTest extends TestDbInit{
	
	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CategoriaDao catDao;
	
	@BeforeEach
	public void setup() {
		catDao.deleteAll(); // Elimino tutte le categorie per testare il DB senza tuple nella tabella
	}
	
	private CategoriaApiTest() { }
	
	@Test
	@DisplayName("CategoriaApiTest - getEmptyCatTest")
	void getEmptyCatTest() {
		
		client.get().uri("/api/categoria")
		.exchange().expectStatus().isNotFound(); // HTTP Resp attesa 404 quando la tabella è vuota
	}
	
	@Test
	@DisplayName("CategoriaApiTest - getNotEmptyCatTest")
	void getNotEmptyCatTest() {
		
		//Inserisco una categoria da prelevare con la chiamata
		Categoria c = new Categoria().setDescrizione("testCat");
		catDao.save(c);
		
		client.get().uri("/api/categoria")
		.exchange().expectStatus().isOk(); // HTTP Resp attesa 200 quando la tabella ha almeno una tupla
		
		client.get().uri("/api/categoria").accept(MediaType.APPLICATION_JSON)
		.exchange().expectBody().jsonPath("$[0].descrizione").isEqualTo(c.getDescrizione());
	}
}
