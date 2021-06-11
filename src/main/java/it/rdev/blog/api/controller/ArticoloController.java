package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.service.impl.ArticoloServiceImpl;

@RestController
@RequestMapping("api")
public class ArticoloController {

	@Autowired
	private ArticoloServiceImpl articoloServiceImpl;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@RequestMapping(value="/articolo", method = RequestMethod.GET)
	public ResponseEntity<?> getAllArticoli( @RequestHeader(name = "Authorization", required = false) String token) {
		
		String username = null;
		if(token!=null && token.startsWith("Bearer")) {
			token = token.replaceAll("Bearer ", "");
			username = jwtUtil.getUsernameFromToken(token);
		}
		
		// Qui posso usare username
		// Se username==null -> utente anonimo
		// Se username!=null -> utente loggato
		
		List<ArticoloDTO> allArtic = null;
		
		if(username!=null) {
			allArtic = articoloServiceImpl.getAllArticoli();
		}
		else { //utente anonimo, prendo solo gli articoli pubblicati
			allArtic = articoloServiceImpl.getAllArticoliPubblicati();
		}
		
		if(allArtic==null)
			return (ResponseEntity<?>) ResponseEntity.notFound();
		else
			return ResponseEntity.ok(allArtic);
	}
}
