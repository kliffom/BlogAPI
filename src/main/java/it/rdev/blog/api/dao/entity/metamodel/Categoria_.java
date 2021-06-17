package it.rdev.blog.api.dao.entity.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.rdev.blog.api.dao.entity.Categoria;

@StaticMetamodel(Categoria.class)
public class Categoria_ {

	public static volatile SingularAttribute<Categoria, String> category;
}
