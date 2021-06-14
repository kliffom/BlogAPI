package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.entity.Articolo;

public interface ArticoloService {

	List<ArticoloDTO> getAllArticoli();
	
	List<ArticoloDTO> getAllArticoliPubblicati();
	
	List<ArticoloDTO> getAllArticoliByUser(String username);
	
	List<ArticoloDTO> getAllArticoliByCategory(String category);
	
	List<ArticoloDTO> getAllArticoliByTag(String tag);
	
	List<ArticoloDTO> getAllArticoliByContenuto(String searchValue);
	
	ArticoloDTO getArticoloById(long id);
	
	Articolo save(ArticoloDTO articolo, String username);
	
	Articolo update(ArticoloDTO articolo, String username);
}
