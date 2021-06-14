package it.rdev.blog.api.dao.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="categoria")
public class Categoria {

	@Id
	@Column(name="descrizione")
	private String descrizione;
	
	@OneToMany(mappedBy="categoria")
	@JsonIgnore
	private List<Articolo> articoli;

	public String getDescrizione() {
		return descrizione;
	}

	public Categoria setDescrizione(String descrizione) {
		this.descrizione = descrizione;
		return this;
	}

	public List<Articolo> getArticoli() {
		return articoli;
	}

	public Categoria setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
		return this;
	}

	@Override
	public String toString() {
		return "Categoria [descrizione=" + descrizione + "]";
	}
	
	
}
