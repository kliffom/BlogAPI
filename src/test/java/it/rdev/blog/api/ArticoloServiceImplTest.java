package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
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
public class ArticoloServiceImplTest {

	ArticoloServiceImpl articoloService;
	
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
		
		Mockito.lenient().when(userDao.findByUsername(user.getUsername())).thenReturn(user);
		Mockito.lenient().when(catDao.findByDescrizione(cat.getDescrizione())).thenReturn(cat);
		Mockito.lenient().when(artDao.findById(art1.get().getId())).thenReturn(art1);
		
		
		articoloService = new ArticoloServiceImpl(artDao, userDao, catDao);
	}
	
	@Test
	@DisplayName("ArticoloServiceImpl - getArticoloByIdTest")
	void getArticoloByIdTest() {
		
		ArticoloDTO artdto = articoloService.getArticoloById((long)1);
		
		assertNotNull(artdto, "Articolo should not be null");
		
		assertEquals(artdto.getTitolo(), "Articolo1", "Articolo title should be 'Articolo1'");
		assertEquals(artdto.getCategoria().getDescrizione(), "CategoriaTest", "Articolo category shoul be 'CategoriaTest'");
		assertEquals(artdto.getUser().getUsername(), "ddinuzzo", "Articolo user name should be 'ddinuzzo'");
		assertEquals(artdto.getTags().get(0).getNome(), "TagTest", "Articolo tag name should be 'TagTest'");
		
	}
}
