package it.rdev.blog.api.dao.entity;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tag")
public class Tag {

	
	@Id
	@Column(name="nome")
	private String nome;
	
	@ManyToMany(mappedBy = "tags")
	@JsonIgnore
	private Set<Articolo> articoli;

	public String getNome() {
		return nome;
	}

	public Tag setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public Tag setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
		return this;
	}

	@Override
	public String toString() {
		return "Tag [nome=" + nome + "]";
	}
	
	
}
