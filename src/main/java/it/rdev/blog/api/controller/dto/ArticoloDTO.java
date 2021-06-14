package it.rdev.blog.api.controller.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ArticoloDTO {

	private long id;
	private String titolo;
	private String sottotitolo;
	private String testo;
	private UserDTO user;
	private CategoriaDTO categoria;
	private List<TagDTO> tags;
	private boolean bozza;
	private Date data_creazione;
	private Date data_pubblicazione;
	private Date data_modifica;
	
	
	public long getId() {
		return id;
	}
	public ArticoloDTO setId(long id) {
		this.id = id;
		return this;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public ArticoloDTO setTitolo(String titolo) {
		this.titolo = titolo;
		return this;
	}
	
	public String getSottotitolo() {
		return sottotitolo;
	}
	public ArticoloDTO setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
		return this;
	}
	
	public String getTesto() {
		return testo;
	}
	public ArticoloDTO setTesto(String testo) {
		this.testo = testo;
		return this;
	}
	
	public boolean isBozza() {
		return bozza;
	}
	public ArticoloDTO setBozza(boolean bozza) {
		this.bozza = bozza;
		return this;
	}
	
	public Date getData_creazione() {
		return data_creazione;
	}
	public ArticoloDTO setData_creazione(Date data_creazione) {
		this.data_creazione = data_creazione;
		return this;
	}
	
	public Date getData_pubblicazione() {
		return data_pubblicazione;
	}
	public ArticoloDTO setData_pubblicazione(Date data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
		return this;
	}
	
	public Date getData_modifica() {
		return data_modifica;
	}
	public ArticoloDTO setData_modifica(Date data_modifica) {
		this.data_modifica = data_modifica;
		return this;
	}
	
	public UserDTO getUser() {
		return user;
	}
	public ArticoloDTO setUser(UserDTO user) {
		this.user = user;
		return this;
	}
	
	public CategoriaDTO getCategoria() {
		return categoria;
	}
	public ArticoloDTO setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
		return this;
	}
	public List<TagDTO> getTags() {
		return tags;
	}
	public ArticoloDTO setTags(List<TagDTO> tags) {
		this.tags = tags;
		return this;
	}
	public ArticoloDTO addTag(TagDTO tag) {
		if(this.tags == null)
			this.tags = new ArrayList<>();
		tags.add(tag);
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticoloDTO other = (ArticoloDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
