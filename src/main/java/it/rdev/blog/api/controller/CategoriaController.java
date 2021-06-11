package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.service.impl.CategoriaServiceImpl;

@RestController
@RequestMapping("api")
public class CategoriaController {
	
	@Autowired
	private CategoriaServiceImpl categoriaServiceImpl;

	@RequestMapping(value="/categoria", method = RequestMethod.GET)
	public ResponseEntity<?> getCategorie() {
		
		final List<CategoriaDTO> allCat = categoriaServiceImpl.getAllCategorie();
		if(allCat!=null)
			return ResponseEntity.ok(allCat);
		else
			return (ResponseEntity<?>) ResponseEntity.notFound();
	}
}
