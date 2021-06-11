package it.rdev.blog.api.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy="articoli")
	private Set<Articoli> articoli;

	public Set<Articoli> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articoli> articoli) {
		this.articoli = articoli;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}