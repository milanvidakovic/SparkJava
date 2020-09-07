package com.minja.beans;


public class Student {
	
	private long id;	
	
	private String ime;
	
	private String prezime;
	
	private NaseljenoMesto mestoRodjenja;
	
	public Student() {}
	
	
	public Student(long id, String ime, String prezime, NaseljenoMesto mestoRodjenja) {
		this();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mestoRodjenja = mestoRodjenja;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public NaseljenoMesto getMestoRodjenja() {
		return mestoRodjenja;
	}

	public void setMestoRodjenja(NaseljenoMesto mestoRodjenja) {
		this.mestoRodjenja = mestoRodjenja;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", ime=" + ime + ", mestoRodjenja=" + mestoRodjenja + ", prezime=" + prezime + "]";
	}
	
}
