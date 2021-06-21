package it.rdev.blog.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.service.impl.CategoriaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

	CategoriaServiceImpl catService;
	
	@Mock CategoriaDao catDao;
	
	@BeforeEach
	public void init() {
		
		Categoria cat = new Categoria().setDescrizione("CategoriaTest");
		
		Mockito.lenient().when(catDao.findByDescrizione("CategoriaTest")).thenReturn(cat);
		
		catService = new CategoriaServiceImpl(catDao);
	}
	
	@Test
	@DisplayName("CategoriaServiceImpl - getCategoriaByDescrizioneTest")
	void getCategoriaByDescrizioneTest() {
		
		CategoriaDTO catdto = catService.getCategoriaByDescrizione("CategoriaTest");
		assertAll(
				() -> assertNotNull(catdto, "Category should not be null"),
				() -> assertEquals(catdto.getDescrizione(), "CategoriaTest", "Category description should be equal to 'CategoriaTest'")
				);
		
		assertThrows(NullPointerException.class, () -> catService.getCategoriaByDescrizione("wrong"), "Non existing category should throw an exception");
	}
}
