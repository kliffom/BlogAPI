package it.rdev.blog.api.service;

import java.util.Optional;
import java.util.Set;

public class ArticoloSearchCriteria {
	
	private Optional<String> searchValue;
	private Optional<Boolean> bozza;
	private Set<Long> ids;
	private Set<String> artCategorie;
	private Set<String> artAutori;
	private Set<String> artTags;
	
	
	public Optional<String> getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(Optional<String> searchValue) {
		this.searchValue = searchValue;
	}
	public Optional<Boolean> getBozza() {
		return bozza;
	}
	public void setBozza(Optional<Boolean> bozza) {
		this.bozza = bozza;
	}
	public Set<Long> getIds() {
		return ids;
	}
	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}
	public Set<String> getArtCategorie() {
		return artCategorie;
	}
	public void setArtCategorie(Set<String> artCategorie) {
		this.artCategorie = artCategorie;
	}
	public Set<String> getArtAutori() {
		return artAutori;
	}
	public void setArtAutori(Set<String> artAutori) {
		this.artAutori = artAutori;
	}
	public Set<String> getArtTags() {
		return artTags;
	}
	public void setArtTags(Set<String> artTags) {
		this.artTags = artTags;
	}

	
}
