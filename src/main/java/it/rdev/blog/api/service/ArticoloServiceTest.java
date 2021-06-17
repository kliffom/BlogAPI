package it.rdev.blog.api.service;

import java.util.List;
import it.rdev.blog.api.dao.entity.Articolo;

public interface ArticoloServiceTest {

	public List<Articolo> retrieveArticoli(ArticoloSearchCriteria searchCriteria);
}
