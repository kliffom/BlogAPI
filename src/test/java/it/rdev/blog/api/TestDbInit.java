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
	
	// Dichiaro gli attributi esternamente in modo da utilizzarli nelle altre classi del package
	
	protected User user1 = new User();
	protected User user2 = new User();
	
	protected Categoria cat1 = new Categoria();
	protected Categoria cat2 = new Categoria();
	
	protected Tag tag1 = new Tag();
	protected Tag tag2 = new Tag();
	protected Tag tag3 = new Tag();
	
	protected List<Tag> tagArt1 = new ArrayList<>();
	protected List<Tag> tagArt2 = new ArrayList<>();
	protected List<Tag> tagArt3 = new ArrayList<>();
	
	protected Articolo art1 = new Articolo();
	protected Articolo art2 = new Articolo();
	protected Articolo art3 = new Articolo();
	
	{
		user1.setUsername("ddinuzzo");
		user1.setPassword("$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K");
		
		user2.setUsername("pangaro");
		user2.setPassword("$2a$10$OQHlM4KBaY4M4beq/S33JeWpgqj0uLBGn9KAxazHvGcYmZ/BEyjvO");
		
		cat1.setDescrizione("Tecnologia");
		cat2.setDescrizione("Musica");
		
		tag1.setNome("Programmazione");
		tag2.setNome("Concerto");
		tag3.setNome("Palco");
		
		tagArt1.add(tag1);
		tagArt1.add(tag2);
		
		tagArt2.add(tag2);
		tagArt2.add(tag3);
		
		tagArt3.add(tag1);
		tagArt3.add(tag3);
		
		art1.setTitolo("Articolo1")
			.setSottotitolo("Sottotitolo1")
			.setTesto("Testo articolo 1")
			.setCategoria(cat1)
			.setUser(user1)
			.setBozza(true)
			.setData_creazione(LocalDateTime.now())
			.setTags(tagArt1);
		
		art2.setTitolo("Articolo2")
			.setSottotitolo("Sottotitolo2")
			.setTesto("Testo articolo 2")
			.setCategoria(cat1)
			.setUser(user2)
			.setBozza(false)
			.setData_creazione(LocalDateTime.now())
			.setTags(tagArt2);
		
		art3.setTitolo("Articolo3")
			.setSottotitolo("Sottotitolo3")
			.setTesto("Testo articolo 3")
			.setCategoria(cat2)
			.setUser(user2)
			.setBozza(true)
			.setData_creazione(LocalDateTime.now())
			.setTags(tagArt3);
	}
	
	@BeforeEach
	public void populate(
			@Autowired UserDao userDao,
			@Autowired TagDao tagDao,
			@Autowired CategoriaDao categoriaDao,
			@Autowired ArticoloDao articoloDao
			) {
		
		userDao.save(user1);
		userDao.save(user2);
		
		categoriaDao.save(cat1);
		categoriaDao.save(cat2);
		
		tagDao.save(tag1);
		tagDao.save(tag2);
		tagDao.save(tag3);
		
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
