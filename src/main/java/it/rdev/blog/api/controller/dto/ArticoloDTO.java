package it.rdev.blog.api.controller.dto;

import java.util.Date;

public class ArticoloDTO {

	private long id;
	private String titolo;
	private String sottotitolo;
	private String testo;
	private boolean bozza;
	private Date data_creazione;
	private Date data_pubblicazione;
	private Date data_modifica;
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
	
	
}
