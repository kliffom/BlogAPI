package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.TagService;

@Service
public class TagServiceImpl implements TagService{

	Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
	
	private TagDao tagDao;
	
	public TagServiceImpl(@Autowired TagDao tagDao) {
		this.tagDao = tagDao;
	}
	
	@Override
	public List<TagDTO> getAllTags() {
		logger.info("getAllTags() called. Retrieving informations.");
		
		List<Tag> allTag = (List<Tag>) tagDao.findAll(); 
		
		List<TagDTO> allTagDto = new ArrayList<>();
		
		for(Tag tag: allTag) {
			TagDTO tagDto = new TagDTO().setNome(tag.getNome());
			allTagDto.add(tagDto);
		}
		
		return allTagDto;
	}

	@Override
	public TagDTO getTagByNome(String nome) {
		logger.info("getTagByNome(" + nome + ") called. Retrieving informations.");

		Tag tag = tagDao.findByNome(nome);
		TagDTO tagDto = new TagDTO().setNome(tag.getNome());
		
		return tagDto;
	}

}
