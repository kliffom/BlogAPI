package it.rdev.blog.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.rdev.blog.api.TestDbInit;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.controller.dto.UserDTO;
import it.rdev.blog.api.dao.ArticoloDao;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.UserDao;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.service.impl.ArticoloServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
public class ArticoloServiceImplTest extends TestDbInit {

	ArticoloServiceImpl articoloService;
	@Autowired
	ArticoloServiceImpl articoloServiceNoMock;
	
	@Mock ArticoloDao artDao;
	@Mock CategoriaDao catDao;
	@Mock UserDao userDao;
	@Mock TagDao tagDao;
	
	@BeforeEach
	public void init() {
		
		Categoria cat = new Categoria().setDescrizione("CategoriaTest");
		User user = new User();
		user.setId(100);
		user.setUsername("ddinuzzo");
		user.setPassword("password_100");
		Tag tag = new Tag().setNome("TagTest");
		List<Tag> tagArt = new ArrayList<>();
		tagArt.add(tag);
		
		Optional<Articolo> art1 = Optional.ofNullable(new Articolo()
				.setId(1)	
				.setTitolo("Articolo1")
				.setSottotitolo("Sottotitolo1")
				.setTesto("Testo articolo 1")
				.setCategoria(cat)
				.setUser(user)
				.setBozza(true)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt));
		
		Optional<Articolo> art2 = Optional.ofNullable(new Articolo()
				.setId(2)
				.setTitolo("Articolo2")
				.setSottotitolo("Sottotitolo2")
				.setTesto("Testo articolo 2")
				.setCategoria(cat)
				.setUser(user)
				.setBozza(false)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt));
		
		List<Articolo> artList = new ArrayList<>();
		artList.add(art1.get());
		artList.add(art2.get());
		
		List<Articolo> artListPub = new ArrayList<>();
		artListPub.add(art2.get());
		
		List<Articolo> artListBozza = new ArrayList<>();
		artListBozza.add(art1.get());
		
		Mockito.lenient().when(userDao.findByUsername(user.getUsername())).thenReturn(user);
		Mockito.lenient().when(catDao.findByDescrizione(cat.getDescrizione())).thenReturn(cat);
		Mockito.lenient().when(artDao.findById(art1.get().getId())).thenReturn(art1);
		
		Mockito.lenient().when(artDao.findAllPubblicati()).thenReturn(artListPub);
		
		Mockito.lenient().when(artDao.findAllByUser(user.getUsername())).thenReturn(artList);
		
		Mockito.lenient().when(artDao.findByCategory(cat.getDescrizione())).thenReturn(artList);
		
		Mockito.lenient().when(artDao.findByTag(tag.getNome())).thenReturn(artList);
		
		Mockito.lenient().when(artDao.findAllByContenuto("Articolo")).thenReturn(artList);
		
		Mockito.lenient().when(artDao.findInBozza(user.getUsername())).thenReturn(artListBozza);
		
		articoloService = new ArticoloServiceImpl(artDao, userDao, catDao);
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getArticoloByIdTest")
	void getArticoloByIdTest() {
		
		ArticoloDTO artdto = articoloService.getArticoloById((long)1);
		
		assertNotNull(artdto, "Articolo should not be null");
		
		articoloDTOGenericTest(artdto, 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
		
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliPubblicatiTest")
	void getAllArticoliPubblicatiTest() {
		
		List<ArticoloDTO> artPubb = articoloService.getAllArticoliPubblicati();
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(artPubb, "Articoli list should not be null");
		assertFalse(artPubb.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se l'elemento nella lista è corretto
		articoloDTOGenericTest(artPubb.get(0), 2, "Articolo2", "Sottotitolo2", "Testo articolo 2", "CategoriaTest", "ddinuzzo", false, "TagTest");
		
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliByUserTest")
	void getAllArticoliByUserTest() {
		
		List<ArticoloDTO> artUsr = articoloService.getAllArticoliByUser("ddinuzzo");
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(artUsr, "Articoli list should not be null");
		assertFalse(artUsr.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloDTOGenericTest(artUsr.get(0), 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
		articoloDTOGenericTest(artUsr.get(1), 2, "Articolo2", "Sottotitolo2", "Testo articolo 2", "CategoriaTest", "ddinuzzo", false, "TagTest");

	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliByCategoryTest")
	void getAllArticoliByCategoryTest() {
		
		List<ArticoloDTO> art = articoloService.getAllArticoliByCategory("CategoriaTest");
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(art, "Articoli list should not be null");
		assertFalse(art.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloDTOGenericTest(art.get(0), 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
		articoloDTOGenericTest(art.get(1), 2, "Articolo2", "Sottotitolo2", "Testo articolo 2", "CategoriaTest", "ddinuzzo", false, "TagTest");
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliByTagTest")
	void getAllArticoliByTagTest() {
		
		List<ArticoloDTO> art = articoloService.getAllArticoliByTag("TagTest");
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(art, "Articoli list should not be null");
		assertFalse(art.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloDTOGenericTest(art.get(0), 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
		articoloDTOGenericTest(art.get(1), 2, "Articolo2", "Sottotitolo2", "Testo articolo 2", "CategoriaTest", "ddinuzzo", false, "TagTest");
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliByContenutoTest")
	void getAllArticoliByContenutoTest() {
		
		List<ArticoloDTO> art = articoloService.getAllArticoliByContenuto("Articolo");
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(art, "Articoli list should not be null");
		assertFalse(art.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloDTOGenericTest(art.get(0), 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
		articoloDTOGenericTest(art.get(1), 2, "Articolo2", "Sottotitolo2", "Testo articolo 2", "CategoriaTest", "ddinuzzo", false, "TagTest");
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getAllArticoliInBozzaTest")
	void getAllArticoliInBozzaTest() {
		
		List<ArticoloDTO> art = articoloService.getAllArticoliInBozza("ddinuzzo");
		
		// Controllo se la lista viene restituita e contiene elementi
		assertNotNull(art, "Articoli list should not be null");
		assertFalse(art.isEmpty(), "Articoli list should not be empty");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloDTOGenericTest(art.get(0), 1, "Articolo1", "Sottotitolo1", "Testo articolo 1", "CategoriaTest", "ddinuzzo", true, "TagTest");
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - saveAndUpdateTest")
	void saveAndUpdateTest() {
		
		UserDTO userdto = new UserDTO();
		userdto.setUsername(user1.getUsername());
		userdto.setPassword(user1.getPassword());
		CategoriaDTO cat1 = new CategoriaDTO().setDescrizione("Tecnologia");
		TagDTO tag1 = new TagDTO().setNome("Programmazione");
		List<TagDTO> tagArt1 = new ArrayList<>();
		tagArt1.add(tag1);
		
		ArticoloDTO art1 = new ArticoloDTO()
				.setTitolo("ArticoloNewDaModificare")
				.setSottotitolo("SottotitoloNew")
				.setTesto("Testo articolo New")
				.setCategoria(cat1)
				.setUser(userdto)
				.setBozza(true)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt1);
		
		Articolo savedArt = articoloServiceNoMock.save(art1, userdto.getUsername());
		
		// Controllo se l'articolo viene restituito
		assertNotNull(savedArt, "Articoli list should not be null");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloGenericTest(savedArt, art1.getTitolo(), art1.getSottotitolo(), art1.getTesto(), cat1.getDescrizione(), userdto.getUsername(), art1.isBozza(), tag1.getNome());
		
		
		// Prendo l'articolo dal DB così da avere l'ID corretto per eseguire la modifica 
		
		List<ArticoloDTO> artList = articoloServiceNoMock.getAllArticoliByContenuto("DaModificare");
		
		ArticoloDTO artMod = artList.get(0);
		
		artMod.setTitolo("ArticoloUpdate")
			.setSottotitolo("SottotitoloUpdate")
			.setTesto("Testo articolo Update")
			.setCategoria(cat1)
			.setUser(userdto)
			.setBozza(true)
			.setData_creazione(LocalDateTime.now())
			.setTags(tagArt1);
		
		Articolo updatedArt = articoloServiceNoMock.update(artMod, userdto.getUsername());
		
		// Controllo se l'articolo viene restituito
		assertNotNull(updatedArt, "Articoli list should not be null");
		
		// Controllo se gli elementi nella lista sono corretti
		articoloGenericTest(updatedArt, artMod.getTitolo(), artMod.getSottotitolo(), artMod.getTesto(), cat1.getDescrizione(), userdto.getUsername(), artMod.isBozza(), tag1.getNome());
		
	}
	
	
	@Test
	@DisplayName("ArticoloServiceImpl - deleteTest")
	void deleteTest() {
		
		// Prelevo un articolo in modo da avere l'ID da eliminare
		List<ArticoloDTO> allArt = articoloServiceNoMock.getAllArticoliByUser(user1.getUsername());
		ArticoloDTO art = allArt.get(0);
		
		articoloServiceNoMock.delete(art.getId());
		
		assertThrows(NoSuchElementException.class, () -> articoloServiceNoMock.getArticoloById(art.getId()), "Get on deleted Articolo should throw an exception");
	}
	
	/**
	 * Funzione contenente tutti gli assert per controllare tutti i campi di un ArticoloDTO
	 * @param artToTest
	 * @param idArticolo
	 * @param titolo
	 * @param sottotitolo
	 * @param testo
	 * @param categoriaDesc
	 * @param username
	 * @param bozza
	 * @param nomeTag
	 */
	private void articoloDTOGenericTest(ArticoloDTO artToTest, long idArticolo, String titolo, String sottotitolo, String testo,
			String categoriaDesc, String username, boolean bozza, String nomeTag) {
		
		assertEquals(artToTest.getId(), idArticolo, "Articolo ID should be +'" + idArticolo + "'");
		assertEquals(artToTest.getTitolo(), titolo, "Articolo title should be +'" + titolo + "'");
		assertEquals(artToTest.getSottotitolo(), sottotitolo, "Articolo subtitle should be +'" + sottotitolo + "'");
		assertEquals(artToTest.getTesto(), testo, "Articolo text should be +'" + testo + "'");
		assertEquals(artToTest.isBozza(), bozza, "Articolo bozza should be +'" + bozza + "'");
		
		assertEquals(artToTest.getCategoria().getDescrizione(), categoriaDesc, "Articolo category name should be +'" + categoriaDesc + "'");
		assertEquals(artToTest.getUser().getUsername(), username, "Articolo user name should be +'" + username + "'");
		assertEquals(artToTest.getTags().get(0).getNome(), nomeTag, "Articolo tag name should be +'" + nomeTag + "'");
	}
	
	/**
	 * Funzione contenente tutti gli assert per controllare tutti i campi di un Articolo
	 * @param artToTest
	 * @param idArticolo
	 * @param titolo
	 * @param sottotitolo
	 * @param testo
	 * @param categoriaDesc
	 * @param username
	 * @param bozza
	 * @param nomeTag
	 */
	private void articoloGenericTest(Articolo artToTest, String titolo, String sottotitolo, String testo,
			String categoriaDesc, String username, boolean bozza, String nomeTag) {
		
		assertEquals(artToTest.getTitolo(), titolo, "Articolo title should be +'" + titolo + "'");
		assertEquals(artToTest.getSottotitolo(), sottotitolo, "Articolo subtitle should be +'" + sottotitolo + "'");
		assertEquals(artToTest.getTesto(), testo, "Articolo text should be +'" + testo + "'");
		assertEquals(artToTest.isBozza(), bozza, "Articolo bozza should be +'" + bozza + "'");
		
		assertEquals(artToTest.getCategoria().getDescrizione(), categoriaDesc, "Articolo category name should be +'" + categoriaDesc + "'");
		assertEquals(artToTest.getUser().getUsername(), username, "Articolo user name should be +'" + username + "'");
		assertEquals(artToTest.getTags().get(0).getNome(), nomeTag, "Articolo tag name should be +'" + nomeTag + "'");
	}
	
}
