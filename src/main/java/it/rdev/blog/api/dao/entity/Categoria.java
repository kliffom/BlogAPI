package it.rdev.blog.api.dao.entity;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="categoria")
public class Categoria {

	@Id
	@Column(name="descrizione")
	private String descrizione;
	
	@OneToMany(mappedBy="articoli")
	private Set<Articolo> articoli;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	
}
