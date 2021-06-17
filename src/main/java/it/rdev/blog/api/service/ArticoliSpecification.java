package it.rdev.blog.api.service;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.SetJoin;

import org.hibernate.mapping.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import it.rdev.blog.api.dao.entity.*;
import it.rdev.blog.api.dao.entity.metamodel.Articolo_;
import it.rdev.blog.api.dao.entity.metamodel.User_;

public class ArticoliSpecification {
	
	public static Specification<Articolo> createArticoliSpecifications(ArticoloSearchCriteria searchCriteria) {
		return autoriIn(searchCriteria.getArtAutori())
			.and(bozzaEqualsTo(searchCriteria.getBozza()))
			.or(textContains(searchCriteria.getSearchValue()))
				;
	}
	
	public static Specification<Articolo> autoriIn(Set<String> autori) {
		if(CollectionUtils.isEmpty(autori))
			return null;
		return (root, query, builder) -> {
			SetJoin<Articolo, User> articoloAutoreJoin = root.join(Articolo_.artAutori);
			return articoloAutoreJoin.get(User_.username).in(autori);
		};
	}
	
	public static Specification<Articolo> bozzaEqualsTo(Optional<Boolean> bozza) {
		if(bozza.get()==null)
			return null;
		return (root, query, builder) -> {
//			return bozza.map(bozz -> builder.equal(root.get(Articolo_.bozza), String.valueOf(bozz)).
//					orElse(null));
//		};
		
			return builder.equal(root.get(Articolo_.bozza), bozza);
		};
	}
	
	public static Specification<Articolo> textContains(Optional<String> searchValue) {
		if(searchValue.get()==null)
			return null;
		return (root, query, builder) ->
		builder.like(root.get(Articolo_.searchValue), contains(searchValue.get()));
	}
	
	private static String contains(String searchValue) {
		return MessageFormat.format("%{0}%", searchValue);
	}

}
