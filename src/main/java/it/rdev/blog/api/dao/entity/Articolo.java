package it.rdev.blog.api.dao.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "articoli")
public class Articolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="titolo")
	private String titolo;
	
	@Column(name="sottotitolo")
	private String sottotitolo;
	
	@Column(name="testo")
	private String testo;
	
	
	@Column(name="bozza")
	private boolean bozza;
	
	@Column(name="data_creazione")
	private Date data_creazione;
	
	@Column(name="data_pubblicazione")
	private Date data_pubblicazione;
	
	@Column(name="data_modifica")
	private Date data_modifica;
	
	@ManyToOne()
	@JoinColumn(name="desc_categoria", referencedColumnName = "descrizione")
	private Categoria categoria;
	
	@ManyToOne()
	@JoinColumn(name="id_users", referencedColumnName = "id")
	private User user;
	
	@ManyToMany
	@JoinTable(
			name="articoli_tag",
			joinColumns = @JoinColumn(name="id_articolo"),
			inverseJoinColumns = @JoinColumn(name="nome_tag")
	)
	private Set<Tag> tags;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getSottotitolo() {
		return sottotitolo;
	}

	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public boolean isBozza() {
		return bozza;
	}

	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}

	public Date getData_creazione() {
		return data_creazione;
	}

	public void setData_creazione(Date data_creazione) {
		this.data_creazione = data_creazione;
	}

	public Date getData_pubblicazione() {
		return data_pubblicazione;
	}

	public void setData_pubblicazione(Date data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}

	public Date getData_modifica() {
		return data_modifica;
	}

	public void setData_modifica(Date data_modifica) {
		this.data_modifica = data_modifica;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
