package it.rdev.blog.api.dao.entity.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import it.rdev.blog.api.dao.entity.User;

@StaticMetamodel(User.class)
public class User_ {

	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> username;
}
