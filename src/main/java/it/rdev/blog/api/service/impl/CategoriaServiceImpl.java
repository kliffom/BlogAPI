package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	public List<CategoriaDTO> getAllCategorie() {
		
		List<Categoria> allCat = (List<Categoria>) categoriaDao.findAll();
		
		List<CategoriaDTO> allCatDto = new ArrayList<>();
		for(Categoria cat: allCat) {
			CategoriaDTO catDto = new CategoriaDTO().setDescrizione(cat.getDescrizione());
			allCatDto.add(catDto);
		}
		
		return allCatDto;
	}

	@Override
	public Categoria getCategoriaByDescrizione(String descrizione) {
		// TODO Auto-generated method stub
		return null;
	}

}
