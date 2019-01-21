package fr.dta.jdbc;

public class Book {
	
	private String titre;
	private String auteur;
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Book(String t, String a) {
		titre =  t;
		auteur = a;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	@Override
	public String toString() {
		return "Book [titre=" + titre + ", auteur=" + auteur + "]";
	}
	
}
