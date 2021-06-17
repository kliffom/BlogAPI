package it.rdev.blog.api.dao.entity.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.rdev.blog.api.dao.entity.Tag;

@StaticMetamodel(Tag.class)
public class Tag_ {

	public static volatile SingularAttribute<Tag, String> tag;
}
