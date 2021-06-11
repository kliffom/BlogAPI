package it.rdev.blog.api.dao.entity;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="tag")
public class Tag {

	
	@Id
	@Column(name="nome")
	private String nome;
	
	@ManyToMany(mappedBy = "articoli")
	private Set<Articoli> articoli;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Articoli> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articoli> articoli) {
		this.articoli = articoli;
	}
	
	
}
