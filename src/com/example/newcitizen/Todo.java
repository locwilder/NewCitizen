package com.example.newcitizen;

public class Todo {

	int id;
    String tododesc;
    int status;
    String created_at;
    String deadline_at;
    String category;
    String remarks;
    String streets;
    String city;
    String areacode;
    String documents;
    int priority;
    
    
    
 
    // constructors
    public Todo() {
    }
 
    
    public Todo(String tododesc, int status, 
			String deadline_at, String category, String remarks,int priority,
			String streets,  String areacode, String city, String documents
			) {
		super();
		this.tododesc = tododesc;
		this.status = status;
		this.deadline_at = deadline_at;
		this.category = category;
		this.remarks = remarks;
		this.streets = streets;
		this.city = city;
		this.areacode = areacode;
		this.documents = documents;
		this.priority = priority;
	}


	public Todo(int id, String tododesc, int status, 
			String deadline_at, String category, String remarks, int priority) {
		this.id = id;
		this.tododesc = tododesc;
		this.status = status;
		this.deadline_at = deadline_at;
		this.category = category;
		this.remarks = remarks;
		this.priority = priority;
		
		
	}
    
    public Todo(String tododesc, int status, 
			String deadline_at, String category, String remarks, int priority) {
		this.tododesc = tododesc;
		this.status = status;
		this.deadline_at = deadline_at;
		this.category = category;
		this.remarks = remarks;
		this.priority = priority;
	}


	public Todo(String note, int status) {
        this.tododesc = note;
        this.status = status;
        
    }
 

 
    // setters
    public void setId(int id) {
        this.id = id;
    }
 
    public void setStatus(int status) {
        this.status = status;
    }
     
    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }
    public void setDeadlineAt(String deadline_at){
        this.deadline_at=deadline_at;
    }
    

	public void setTododesc(String tododesc) {
		this.tododesc = tododesc;
	}


	public void setDeadline_at(String deadline_at) {
		this.deadline_at = deadline_at;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	// getters
    public int getId() {
        return this.id;
    }
 
 
    public int getStatus() {
        return this.status;
    }
    
    public String getTododesc() {
		return tododesc;
	}
    
    public int getPriority() {
		return priority;
	}
    
    public String getRemarks() {
		return remarks;
	}
    
    public String getCategory() {
		return category;
	}
    
	public String getDeadline_at() {
		return deadline_at;
	}


	public String getStreets() {
		return streets;
	}


	public String getCity() {
		return city;
	}


	public String getAreacode() {
		return areacode;
	}


	public String getDocuments() {
		return documents;
	}


	public void setStreets(String streets) {
		this.streets = streets;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}


	public void setDocuments(String documents) {
		this.documents = documents;
	}
	
}
