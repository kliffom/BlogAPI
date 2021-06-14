package it.rdev.blog.api.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import it.rdev.blog.api.service.ArticoloService;

@Service
public class ArticoloServiceImpl implements ArticoloService{

	
	Logger logger = LoggerFactory.getLogger(ArticoloServiceImpl.class);
	
	@Autowired
	private ArticoloDao articoloDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	public List<ArticoloDTO> getAllArticoli() {
		
		logger.info("getAllArticoli() called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAll();
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		return allArticoliDto;
	}

	@Override
	public List<ArticoloDTO> getAllArticoliPubblicati() {
		
		logger.info("getAllArticoliPubblicati() called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAllPubblicati();
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		
		return allArticoliDto;
	}

	@Override
	public List<ArticoloDTO> getAllArticoliByUser(String username) {
		
		logger.info("getAllArticoliByUser(" + username + ") called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findByAutore(username);
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		
		return allArticoliDto;
	}
	
	@Override
	public List<ArticoloDTO> getAllArticoliByCategory(String category) {
		
		logger.info("getAllArticoliByCategory(" + category + ") called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findByCategory(category);
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		
		return allArticoliDto;
	}

	@Override
	public List<ArticoloDTO> getAllArticoliByTag(String tag) {
		
		logger.info("getAllArticoliByTag(" + tag + ") called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findByTag(tag);
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		
		return allArticoliDto;
	}

	
	@Override
	public List<ArticoloDTO> getAllArticoliByContenuto(String searchValue) {
		
		logger.info("getAllArticoliByContenuto(" + searchValue + ") called. Retrieving informations.");
		
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAllByContenuto(searchValue);
		
		List<ArticoloDTO> allArticoliDto = convertListArticoloToDTO(allArticoli);
		
		return allArticoliDto;
	}

	@Override
	public ArticoloDTO getArticoloById(long id) {
	
		logger.info("getArticoloById(" + id + ") called. Retrieving informations.");
		Optional<Articolo> artResult = articoloDao.findById(id);
		Articolo art = artResult.get();
		
		ArticoloDTO artDto = convertArticoloToDTO(art);
		
		return artDto;
	}
	
	public Articolo save(ArticoloDTO articolo, String username) {
		
		logger.info("save(" + articolo.getTitolo() + ", " + username + ") called. Retrieving informations.");
		
		Articolo artic = new Articolo()
				.setTitolo(articolo.getTitolo())
				.setSottotitolo(articolo.getSottotitolo())
				.setTesto(articolo.getTesto())
				.setBozza(true)
				.setData_creazione(LocalDateTime.now());
		
		User user = userDao.findByUsername(username);
		Categoria cat = new Categoria().setDescrizione(articolo.getCategoria().getDescrizione());
		List<TagDTO> tags = articolo.getTags();
		List<Tag> tagsEn = new ArrayList<>();
		for(TagDTO tag: tags) {
			Tag tagEn = new Tag().setNome(tag.getNome());
			tagsEn.add(tagEn);
		}
		
		
		artic.setUser(user);
		artic.setCategoria(cat);
		artic.setTags(tagsEn);
		
		logger.info(artic.toString());
				
		
		//Invocare qui ArticoloDao.save(artic); // Per rendere persistente l'oggetto nel DB
		return articoloDao.save(artic);
	}

	public Articolo update(ArticoloDTO articolo, String username) {
		logger.info("update(" + articolo.getTitolo() + ", " + username + ") called. Retrieving informations.");
		
		Articolo artic = new Articolo()
				.setTitolo(articolo.getTitolo())
				.setSottotitolo(articolo.getSottotitolo())
				.setTesto(articolo.getTesto())
				.setBozza(articolo.isBozza())
				.setData_creazione(articolo.getData_creazione())
				.setData_modifica(LocalDateTime.now());
		
		if(!articolo.isBozza())		// Se l'articolo non è più in stato di bozza
			if(artic.getData_pubblicazione()==null)		// E non era ancora stato pubblicato
				artic.setData_pubblicazione(LocalDateTime.now()); 	// Inserisco la data di pubblicazione
		
		User user = userDao.findByUsername(username);
		Categoria cat = new Categoria().setDescrizione(articolo.getCategoria().getDescrizione());
		List<TagDTO> tags = articolo.getTags();
		List<Tag> tagsEn = new ArrayList<>();
		for(TagDTO tag: tags) {
			Tag tagEn = new Tag().setNome(tag.getNome());
			tagsEn.add(tagEn);
		}
		
		
		artic.setUser(user);
		artic.setCategoria(cat);
		artic.setTags(tagsEn);
		
		logger.info(artic.toString());
				
		return articoloDao.save(artic);
	}
	
	private List<ArticoloDTO> convertListArticoloToDTO(List<Articolo> allArticoli) {
		
		List<ArticoloDTO> allArticoliDto = new ArrayList<>();
		for(Articolo art: allArticoli) {
			
			User userEn = userDao.findByUsername(art.getUser().getUsername());
			UserDTO user = new UserDTO().setUsername(userEn.getUsername());
			
			Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
			CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
			
			List<Tag> tagsEn = art.getTags();
			List<TagDTO> tagsDTO = new ArrayList<>();
			
			for(Tag tag: tagsEn) {
				tagsDTO.add(new TagDTO().setNome(tag.getNome()));
			}
			
			ArticoloDTO artDto = new ArticoloDTO()
					.setId(art.getId())
					.setTitolo(art.getTitolo())
					.setSottotitolo(art.getSottotitolo())
					.setTesto(art.getTesto())
					.setBozza(art.isBozza())
					.setData_creazione(art.getData_creazione())
					.setData_pubblicazione(art.getData_pubblicazione())
					.setData_modifica(art.getData_modifica())
					.setUser(user) 
					.setCategoria(categoria)
					.setTags(tagsDTO);
			allArticoliDto.add(artDto);
		}
		
		return allArticoliDto;
	}
	
	private ArticoloDTO convertArticoloToDTO(Articolo art) {
		
		User userEn = userDao.findByUsername(art.getUser().getUsername());
		UserDTO user = new UserDTO().setUsername(userEn.getUsername());
		
		Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
		CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
		
		List<Tag> tagsEn = art.getTags();
		List<TagDTO> tagsDTO = new ArrayList<>();
		
		for(Tag tag: tagsEn) {
			tagsDTO.add(new TagDTO().setNome(tag.getNome()));
		}
		
		ArticoloDTO artDto = new ArticoloDTO()
				.setId(art.getId())
				.setTitolo(art.getTitolo())
				.setSottotitolo(art.getSottotitolo())
				.setTesto(art.getTesto())
				.setBozza(art.isBozza())
				.setData_creazione(art.getData_creazione())
				.setData_pubblicazione(art.getData_pubblicazione())
				.setData_modifica(art.getData_modifica())
				.setUser(user)
				.setCategoria(categoria)
				.setTags(tagsDTO);
		
		return artDto;
	}

	
}
