package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
		
		List<ArticoloDTO> allArticoliDto = new ArrayList<>();
		for(Articolo art: allArticoli) {
			
			User userEn = userDao.findByUsername(art.getUser().getUsername());
			UserDTO user = new UserDTO().setUsername(userEn.getUsername());
			
			Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
			CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
			
			
			
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
					.setCategoria(categoria);
			allArticoliDto.add(artDto);
		}
		
		return allArticoliDto;
	}

	@Override
	public List<ArticoloDTO> getAllArticoliPubblicati() {
		
		logger.info("getAllArticoliPubblicati() called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAllPubblicati();
		
		List<ArticoloDTO> allArticoliDto = new ArrayList<>();
		for(Articolo art: allArticoli) {
			
			User userEn = userDao.findByUsername(art.getUser().getUsername());
			UserDTO user = new UserDTO().setUsername(userEn.getUsername());
			
			Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
			CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
			
			Set<Tag> tagsEn = art.getTags();
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

	@Override
	public List<ArticoloDTO> getAllArticoliByUser(String username) {
		
		logger.info("getAllArticoliByUser(" + username + ") called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAllByUser(username);
		
		List<ArticoloDTO> allArticoliDto = new ArrayList<>();
		for(Articolo art: allArticoli) {
			
			User userEn = userDao.findByUsername(art.getUser().getUsername());
			UserDTO user = new UserDTO().setUsername(userEn.getUsername());
			
			Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
			CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
			
			Set<Tag> tagsEn = art.getTags();
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

	@Override
	public ArticoloDTO getArticoloById(long id) {
	
		logger.info("getArticoloById(" + id + ") called. Retrieving informations.");
		Optional<Articolo> artResult = articoloDao.findById(id);
		Articolo art = artResult.get();
		
		User userEn = userDao.findByUsername(art.getUser().getUsername());
		UserDTO user = new UserDTO().setUsername(userEn.getUsername());
		
		Categoria catEn = categoriaDao.findByDescrizione(art.getCategoria().getDescrizione());
		CategoriaDTO categoria = new CategoriaDTO().setDescrizione(catEn.getDescrizione());
		
		Set<Tag> tagsEn = art.getTags();
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
