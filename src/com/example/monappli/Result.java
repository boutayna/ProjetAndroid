package com.example.monappli;

public class Result {
	private int id;
	private String name;
	private String secteur;
	private String quartier;
	private String informations;
	private String urlImage;
	private Double lon;
	private Double lat;

	
	@Override
	public String toString() {
		return "Result [name=" + name + ", secteur=" + secteur + ", quartier="
				+ quartier + ", informations=" + informations + ", urlImage="
				+ urlImage + "]";
	}
	public int getId(){
		return id;
	}
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Result(String name, String secteur, String quartier,
			String informations, String urlImage) {
		super();
		this.name = name;
		this.secteur = secteur;
		this.quartier = quartier;
		this.informations = informations;
		this.urlImage = urlImage;
	}

	public Result(int id,String name, String secteur, String quartier,
			String informations, String urlImage, Double lon, Double lat) {
		super();
		this.id=id;
		this.name = name;
		this.secteur = secteur;
		this.quartier = quartier;
		this.informations = informations;
		this.lon = lon;
		this.lat = lat;
		this.urlImage = urlImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getInformations() {
		return informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

}
