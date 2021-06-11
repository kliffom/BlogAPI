package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.ArticoloDao;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.service.ArticoloService;

@Service
public class ArticoloServiceImpl implements ArticoloService{

	
	Logger logger = LoggerFactory.getLogger(ArticoloServiceImpl.class);
	
	@Autowired
	private ArticoloDao articoloDao;
	
	@Override
	public List<ArticoloDTO> getAllArticoli() {
		
		logger.info("getAllArticoli() called. Retrieving informations.");
		List<Articolo> allArticoli = (List<Articolo>) articoloDao.findAll();
		
		List<ArticoloDTO> allArticoliDto = new ArrayList<>();
		for(Articolo art: allArticoli) {
			ArticoloDTO artDto = new ArticoloDTO()
					.setId(art.getId())
					.setTitolo(art.getTitolo())
					.setSottotitolo(art.getSottotitolo())
					.setTesto(art.getTesto())
					.setBozza(art.isBozza())
					.setData_creazione(art.getData_creazione())
					.setData_pubblicazione(art.getData_pubblicazione())
					.setData_modifica(art.getData_modifica());
			allArticoliDto.add(artDto);
		}
		
		return allArticoliDto;
	}

}
