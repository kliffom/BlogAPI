package it.rdev.blog.api.dao.entity;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="tag")
public class Tag {

	
	@Id
	@Column(name="nome")
	private String nome;
	
	@ManyToMany(mappedBy = "tags")
	private Set<Articolo> articoli;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	
}
