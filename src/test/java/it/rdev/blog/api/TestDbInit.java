package it.rdev.blog.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import it.rdev.blog.api.dao.ArticoloDao;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.UserDao;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.User;

@Sql({"/database_init.sql"})
public class TestDbInit {
	
	@BeforeEach
	public void populate(
			@Autowired UserDao userDao,
			@Autowired TagDao tagDao,
			@Autowired CategoriaDao categoriaDao,
			@Autowired ArticoloDao articoloDao
			) {
		// Creo e salvo utenti
		
		User user1 = new User();
		user1.setUsername("ddinuzzo");
		user1.setPassword("$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K");
		
		User user2 = new User();
		user2.setUsername("pangaro");
		user2.setPassword("$2a$10$OQHlM4KBaY4M4beq/S33JeWpgqj0uLBGn9KAxazHvGcYmZ/BEyjvO");
		
		userDao.save(user1);
		userDao.save(user2);
		
		Categoria cat1 = new Categoria().setDescrizione("Tecnologia");
		Categoria cat2 = new Categoria().setDescrizione("Musica");
		
		categoriaDao.save(cat1);
		categoriaDao.save(cat2);
		
		Tag tag1 = new Tag().setNome("Programmazione");
		Tag tag2 = new Tag().setNome("Concerto");
		Tag tag3 = new Tag().setNome("Palco");
		
		tagDao.save(tag1);
		tagDao.save(tag2);
		tagDao.save(tag3);
		
		List<Tag> tagArt1 = new ArrayList<>();
		tagArt1.add(tag1);
		tagArt1.add(tag2);
		
		List<Tag> tagArt2 = new ArrayList<>();
		tagArt2.add(tag2);
		tagArt2.add(tag3);
		
		List<Tag> tagArt3 = new ArrayList<>();
		tagArt3.add(tag1);
		tagArt3.add(tag3);
		
		Articolo art1 = new Articolo()
				.setTitolo("Articolo1")
				.setSottotitolo("Sottotitolo1")
				.setTesto("Testo articolo 1")
				.setCategoria(cat1)
				.setUser(user1)
				.setBozza(true)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt1);
		
		Articolo art2 = new Articolo()
				.setTitolo("Articolo2")
				.setSottotitolo("Sottotitolo2")
				.setTesto("Testo articolo 2")
				.setCategoria(cat1)
				.setUser(user2)
				.setBozza(true)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt2);
		
		Articolo art3 = new Articolo()
				.setTitolo("Articolo3")
				.setSottotitolo("Sottotitolo3")
				.setTesto("Testo articolo 3")
				.setCategoria(cat2)
				.setUser(user2)
				.setBozza(true)
				.setData_creazione(LocalDateTime.now())
				.setTags(tagArt3);
		
		articoloDao.save(art1);
		articoloDao.save(art2);
		articoloDao.save(art3);
		
	}
	
	@AfterEach
	public void destroy(
			@Autowired UserDao userDao,
			@Autowired TagDao tagDao,
			@Autowired CategoriaDao categoriaDao,
			@Autowired ArticoloDao articoloDao			
			) {
		userDao.deleteAll();
		tagDao.deleteAll();
		articoloDao.deleteAll();
		categoriaDao.deleteAll();
	}

}
