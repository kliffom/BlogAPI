package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.ArticoloDTO;

public interface ArticoloService {

	List<ArticoloDTO> getAllArticoli();
	
	List<ArticoloDTO> getAllArticoliPubblicati();
	
	List<ArticoloDTO> getAllArticoliByUser(String username);
	
	List<ArticoloDTO> getAllArticoliByContenuto(String searchValue);
	
	ArticoloDTO getArticoloById(long id);
}
