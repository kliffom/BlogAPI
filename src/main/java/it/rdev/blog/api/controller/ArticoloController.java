package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.service.impl.ArticoloServiceImpl;

@RestController
@RequestMapping("api")
public class ArticoloController {

	@Autowired
	private ArticoloServiceImpl articoloServiceImpl;
	
	@RequestMapping(value="/articolo", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticoli() {
		
		final List<ArticoloDTO> allArtic = articoloServiceImpl.getAllArticoli();
		
		if(allArtic==null)
			return (ResponseEntity<?>) ResponseEntity.notFound();
		else
			return ResponseEntity.ok(allArtic);
	}
}
