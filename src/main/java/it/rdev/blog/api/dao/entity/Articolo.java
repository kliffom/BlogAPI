package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
	private LocalDateTime data_creazione;
	
	@Column(name="data_pubblicazione")
	private LocalDateTime data_pubblicazione;
	
	@Column(name="data_modifica")
	private LocalDateTime data_modifica;
	
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
	private List<Tag> tags;

	public long getId() {
		return id;
	}

	public Articolo setId(long id) {
		this.id = id;
		return this;
	}

	public String getTitolo() {
		return titolo;
	}

	public Articolo setTitolo(String titolo) {
		this.titolo = titolo;
		return this;
	}

	public String getSottotitolo() {
		return sottotitolo;
	}

	public Articolo setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
		return this;
	}

	public String getTesto() {
		return testo;
	}

	public Articolo setTesto(String testo) {
		this.testo = testo;
		return this;
	}

	public boolean isBozza() {
		return bozza;
	}

	public Articolo setBozza(boolean bozza) {
		this.bozza = bozza;
		return this;
	}

	public LocalDateTime getData_creazione() {
		return data_creazione;
	}

	public Articolo setData_creazione(LocalDateTime data_creazione) {
		this.data_creazione = data_creazione;
		return this;
	}

	public LocalDateTime getData_pubblicazione() {
		return data_pubblicazione;
	}

	public Articolo setData_pubblicazione(LocalDateTime data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
		return this;
	}

	public LocalDateTime getData_modifica() {
		return data_modifica;
	}

	public Articolo setData_modifica(LocalDateTime data_modifica) {
		this.data_modifica = data_modifica;
		return this;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Articolo setCategoria(Categoria categoria) {
		this.categoria = categoria;
		return this;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public Articolo setTags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Articolo setUser(User user) {
		this.user = user;
		return this;
	}

	@Override
	public String toString() {
		return "Articolo [id=" + id + ", titolo=" + titolo + ", sottotitolo=" + sottotitolo + ", testo=" + testo
				+ ", bozza=" + bozza + ", data_creazione=" + data_creazione + ", data_pubblicazione="
				+ data_pubblicazione + ", data_modifica=" + data_modifica + ", categoria=" + categoria + ", user="
				+ user + ", tags=" + tags + "]";
	}
	
	
}
