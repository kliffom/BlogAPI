package it.rdev.blog.api.dao.entity.metamodel;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.User;

@StaticMetamodel(Articolo.class)
public class Articolo_ {
	
	// Set di ID da poter ricercare
	public static volatile SetAttribute<Articolo, Long> ids;
	
	// Unico valore di ricerca per titolo, sottotitolo e testo
	public static volatile SingularAttribute<Articolo, String> searchValue;
	
	// Unico valore di ricerca per articoli in bozza
	public static volatile SingularAttribute<Articolo, Boolean> bozza;

	// Set di categorie da ricercare
	public static volatile SetAttribute<Articolo, Categoria> artCategorie;

	// Set di autori da ricercare	
	public static volatile SetAttribute<Articolo, User> artAutori;
	
	// Set di tag da ricercare
	public static volatile SetAttribute<Articolo, Tag> artTags;
}
