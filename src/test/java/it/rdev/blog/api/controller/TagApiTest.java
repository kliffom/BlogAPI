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
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.entity.Tag;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("<= TagAPITest =>")
public class TagApiTest extends TestDbInit{

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private TagDao tagDao;
	
	@BeforeEach
	public void setup() {
		tagDao.deleteAll(); // Elimino tutti i tag inseriti da TestDbInit per testare con DB vuoto 
	}
	
	private TagApiTest() { }
	
	@Test
	@DisplayName("TagApiTest - getEmptyTest")
	void getEmptyTest() {
		
		client.get().uri("/api/tag")
		.exchange().expectStatus().isNotFound(); //Atteso errore 404 quando la tabella Tag è vuota
		
	}
	
	@Test
	@DisplayName("TagApiTest - getNotEmptyTest")
	void getNotEmptyTest() {
		
		//Inserisco un tag per vedere se viene restituito
		
		Tag t = new Tag().setNome("testTag");
		tagDao.save(t);
		
		client.get().uri("/api/tag")
		.exchange().expectStatus().isOk(); // Atteso codice 200 se ci sono tag
		
		
		client.get().uri("/api/tag").accept(MediaType.APPLICATION_JSON)
		.exchange().expectStatus().isOk()
		.expectBody().jsonPath("$[0].nome").isEqualTo(t.getNome());
		
	}
	
}
