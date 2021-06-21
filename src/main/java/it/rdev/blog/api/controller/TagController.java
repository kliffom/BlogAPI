package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.service.impl.TagServiceImpl;

@RestController
@RequestMapping("api")
public class TagController {

	@Autowired
	private TagServiceImpl tagServiceImpl;
	
	@RequestMapping(value="/tag", method = RequestMethod.GET)
	public ResponseEntity<?> getTags() {
		
		final List<TagDTO> allTag = tagServiceImpl.getAllTags();
		if(allTag!=null && !allTag.isEmpty()) 
			return ResponseEntity.ok(allTag);
		else 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun tag presente.");
	}
}
