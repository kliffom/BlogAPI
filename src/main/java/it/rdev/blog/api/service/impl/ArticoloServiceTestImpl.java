package it.rdev.blog.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.dao.ArticoloDAOTest;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.service.ArticoliSpecification;
import it.rdev.blog.api.service.ArticoloSearchCriteria;
import it.rdev.blog.api.service.ArticoloServiceTest;

@Service
public class ArticoloServiceTestImpl implements ArticoloServiceTest{

	@Autowired
	private ArticoloDAOTest articoloDaoTest;
	
	@Override
	public List<Articolo> retrieveArticoli(ArticoloSearchCriteria searchCriteria) {
		Specification<Articolo> artSpecifications = ArticoliSpecification.createArticoliSpecifications(searchCriteria);
		return this.articoloDaoTest.findAll(artSpecifications);
	}

}
