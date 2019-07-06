package com.avdo;

public class User {
	private int id;
    
    private String name;
     
    private String department;
    
    private long salary;
    
    public User(int id, String name, String department, long salary) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}
    
    public String getTheName() {
        return name;
      }
    
    

}
