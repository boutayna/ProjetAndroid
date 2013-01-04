package com.example.monappli;

public class Result {

		@Override
	public String toString() {
		return "Result [name=" + name + ", secteur=" + secteur + ", quartier="
				+ quartier + ", informations=" + informations + ", urlImage="
				+ urlImage + "]";
	}
		private String name;
		private String secteur;
		private String quartier;
		private String informations;
		private String urlImage;
		private Long lon;
		public Long getLon() {
			return lon;
		}
		public void setLon(Long lon) {
			this.lon = lon;
		}
		public float getLat() {
			return lat;
		}
		public void setLat(Long lat) {
			this.lat = lat;
		}
		private float lat;
		public Result(String name, String secteur, String quartier,String informations,String urlImage) {
			super();
			this.name = name;
			this.secteur = secteur;
			this.quartier = quartier;
			this.informations = informations;
			this.urlImage = urlImage;
		}
		public Result(String name, String secteur, String quartier,String urlImage,Long lon,Long lat) {
			super();
			this.name = name;
			this.secteur = secteur;
			this.quartier = quartier;
			this.lon=lon;
			this.lat=lat;
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
