package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.TagDTO;

public interface TagService {

	List<TagDTO> getAllTags();
	
	TagDTO getTagByNome(String nome);
}
