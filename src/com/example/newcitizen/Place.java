package com.example.newcitizen;

public class Place {

	int id;
	String place_name;
	String place_adress;
	String place_street;
	String place_areacode;
	String place_city;
	
	public Place(){
		
	}
	
	public Place(String place_name, String place_adress,
			String place_street, String place_areacode, String place_city) {
		this.place_name = place_name;
		this.place_adress = place_adress;
		this.place_street = place_street;
		this.place_areacode = place_areacode;
		this.place_city = place_city;
	}
	
	public Place(int id, String where_name, String where_adress,
			String where_street, String where_areacode, String where_city) {
		this.id = id;
		this.place_name = where_name;
		this.place_adress = where_adress;
		this.place_street = where_street;
		this.place_areacode = where_areacode;
		this.place_city = where_city;
	}

	//getters
	public int getId() {
		return id;
	}

	public String getWhere_name() {
		return place_name;
	}

	public String getWhere_adress() {
		return place_adress;
	}

	public String getWhere_street() {
		return place_street;
	}

	public String getWhere_areacode() {
		return place_areacode;
	}

	public String getWhere_city() {
		return place_city;
	}
 
	//setters
	public void setId(int id) {
		this.id = id;
	}

	public void setWhere_name(String where_name) {
		this.place_name = where_name;
	}

	public void setWhere_adress(String where_adress) {
		this.place_adress = where_adress;
	}

	public void setWhere_street(String where_street) {
		this.place_street = where_street;
	}

	public void setWhere_areacode(String where_areacode) {
		this.place_areacode = where_areacode;
	}

	public void setWhere_city(String where_city) {
		this.place_city = where_city;
	}
	
	
	
	
}

