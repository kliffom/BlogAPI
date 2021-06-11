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
	
	
}
