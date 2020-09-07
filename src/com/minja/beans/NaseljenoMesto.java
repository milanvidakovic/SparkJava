package com.minja.beans;

public class NaseljenoMesto {
	
	private long id;
	private String naziv;
	
	public NaseljenoMesto() {}

	public NaseljenoMesto(long id, String naziv) {
		this();
		this.id = id;
		this.naziv = naziv;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
}
