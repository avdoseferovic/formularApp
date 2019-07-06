package com.avdo;

import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbconnect {
	
	//Basic connection using HSQLDB
	public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            con = DriverManager.getConnection(
                "jdbc:hsqldb:mem:avdosdb", "sa", "");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }

	
	
	//Creating tables on start
	public static Connection createTables() {
	      
	      Statement stmt = null;
	      int result = 0;
	      
	      try {
	    	  Connection connection = getConnection();
	    	  
	    	  try {
	 	        stmt = connection.createStatement();
	 	         
	 	        result = stmt.executeUpdate(""
	 	        		
	 	        		// Creating form table
	 	        		+ "CREATE TABLE IF NOT EXISTS form ("
	 	        		+ "form_version VARCHAR(50),"
	 	        		+ "form_name VARCHAR(50) NOT NULL,"
	 	        		+ "field_id INT NOT NULL,"
	 	        		+ "field_value VARCHAR(50) NOT NULL,"
	 	        		+ "CONSTRAINT PK_form PRIMARY KEY (form_version, field_id)); "
	 	        		
	 	        		// Creating template_fields table
	 	        		+ "CREATE TABLE IF NOT EXISTS template_fields ("
	 	        		+ "field_id INT NOT NULL,"
	 	        		+ "form_name VARCHAR(50) NOT NULL,"
	 	        		+ "field_name VARCHAR(50) NOT NULL,"
	 	        		+ "field_value VARCHAR(50) NOT NULL,"
	 	        		+ "field_type VARCHAR(50) NOT NULL,"
	 	        		+ "field_mandatory VARCHAR(50) NOT NULL,"
	 	        		+ "CONSTRAINT PK_field PRIMARY KEY (field_id,form_name)); "
	 	        		
	 	        		// Creating radio_fields table
	 	        		+ "CREATE TABLE IF NOT EXISTS radio_fields ("
	 	        		+ "radio_id INTEGER IDENTITY PRIMARY KEY,"
	 	        		+ "field_id INTEGER NOT NULL,"
	 	        		+ "form_name VARCHAR(50) NOT NULL,"
	 	        		+ "radio_value VARCHAR(50) NOT NULL,"
	 	        		+ "UNIQUE(field_id, radio_value, form_name)); "
	 	        		
	 	        		//Creating template_fields_options table
	 	        		+ "CREATE TABLE IF NOT EXISTS template_fields_options ("
	 	        		+ "option_id INTEGER IDENTITY PRIMARY KEY,"
	 	        		+ "option_value VARCHAR(50) NOT NULL); "
	 	        		+ "");
	 	        
	 	        connection.close();
	 	        stmt.close();
	    	  }  catch (SQLException e) {
	                e.printStackTrace();
	 	      }
	 	      System.out.println("Tables created successfully");
	 	      return connection;
	 	      
	      }
	      catch (Exception e) {
	            e.printStackTrace();
	      return null;
	     
	   }
	}
	
	//Insert radios, because we save it in specific table due to multiple radio options
	public static void insertRadios(Field field) {
		
		  Connection con = null; 
	      Statement stmt = null; 
	      Statement delstmt = null;
	      int i;
	      int result = 0; 
	      try { 
	         Class.forName("org.hsqldb.jdbc.JDBCDriver"); 
	         con = DriverManager.getConnection("jdbc:hsqldb:mem:avdosdb", "SA", "");
	         stmt = con.createStatement();
	         
	         //Temporary fix for duplicate radio entries
	         delstmt = con.createStatement();
	         delstmt.executeUpdate("DELETE FROM radio_fields WHERE field_id = '"+field.getTheId()+"' AND form_name = '"+field.getTheFormName()+"'");
	         
	         //Loop through radio fields array sent from web and insert them, requires rework later
	         for (i = 0; i < field.getTheRadios().length; i++) {
		         result = stmt.executeUpdate("INSERT INTO radio_fields (field_id, form_name, radio_value) VALUES ('"+field.getTheId()+"', '"+field.getTheFormName()+"', '"+field.getTheRadios()[i]+"')");
		         
		         System.out.println(field.getTheId() + ": " + field.getTheRadios()[i] + ": -- " + field.getTheFormName() );
	         }  
	         con.commit(); 
	      }catch (Exception e) { 
	         e.printStackTrace(System.out); 
	      } 
	      System.out.println(result+" rows effected"); 
	   } 
	//Method that inserts forms
	public static void insertForm(Form form) {
		
		try {
			Connection connection = getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO form (form_version, form_name, field_id, field_value) "
						+ "VALUES ('"+form.getTheVersion()+"','"+form.getTheName()+"', '"+form.getTheFieldId()+"', '"+form.getTheFieldValue()+"')");
				
				statement.executeUpdate();

                statement.close();
                connection.close();
				
			}catch(SQLException ex) {
				
			}
			
			
		}catch (Exception e) {
            e.printStackTrace();
       }
		
	}
	
	//Method that inserts fields into template_fields
	public static void insertField(Field field) {
		
		try{
            Connection connection = getConnection();


            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO template_fields (field_id, form_name, field_name, field_value, field_type, field_mandatory)"
                														+ " VALUES ('"+field.getTheId()+"', '"+field.getTheFormName()+"', '"+field.getTheName()+"', 'Here is text 424', '"+field.getTheType()+"', '"+field.getTheMandatory()+"')"); 
                statement.executeUpdate();

                statement.close();
                connection.close();
            } catch (SQLException ex) {
            	//23 stands for duplicate / unique entries in db, so listen for that error and update db if so
            	if (ex.getSQLState().startsWith("23")) {
                    System.out.println("Duplicate");
                    PreparedStatement statement = connection.prepareStatement("UPDATE template_fields SET "
                    		+ "field_id = '"+field.getTheId()+"', field_name = '"+field.getTheName()+"',"
                    		+ " field_value = 'here text', field_type = '"+field.getTheType()+"'"
                    				+ "WHERE field_id = '"+field.getTheId()+"' "); 
					statement.executeUpdate();
					
					statement.close();
					connection.close();
            	}
            }

        } catch (Exception e) {
            e.printStackTrace();
       }
	} 
	
	
	
	 public static List<Form> list() {
	        try{
	            Connection connection = getConnection();

	            List<Form> forms = new ArrayList<Form>();

	            try {
	                PreparedStatement statement = connection.prepareStatement("SELECT * FROM form");
	                ResultSet rs = statement.executeQuery();

	                while (rs.next()) {
	                    String formVersion = rs.getString("form_version");
	                    String formName = rs.getString("form_name");
	                    int fieldId = rs.getInt("field_id");
	                    String fieldValue = rs.getString("field_value");
	                    Form form = new Form(formVersion, formName, fieldId, fieldValue);
	                    System.out.println("form:" + form.getTheName());
	                    forms.add(form);
	                }
	                statement.close();
	                connection.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }

	            return forms;
	        } catch (Exception e) {
	            e.printStackTrace();
	           return null;
	       }
	    }
	 
	 //Get only form names, saves JSON data
	 public static List<String> form_list() {
	        try{
	            Connection connection = getConnection();

	            List<String> forms = new ArrayList<String>();

	            try {
	                PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT form_name FROM template_fields");
	                ResultSet rs = statement.executeQuery();

	                while (rs.next()) {
	                    String form = rs.getString("form_name");
	                    forms.add(form);
	                }
	                statement.close();
	                connection.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }

	            return forms;
	        } catch (Exception e) {
	            e.printStackTrace();
	           return null;
	       }
	    }
	 //Get form template based on certain version
	 //This method is used on LoadFormData.java
	 public static List<Form> list_form(String form_Version) {
	        try{
	            Connection connection = getConnection();

	            List<Form> forms = new ArrayList<Form>();
	            

	            try {
	            	PreparedStatement statement;

		            statement = connection.prepareStatement("SELECT * FROM form WHERE form_version = '"+form_Version+"'");
	            	ResultSet rs = statement.executeQuery();

	                while (rs.next()) {
	                	String formVersion = rs.getString("form_version");
	                    String formName = rs.getString("form_name");
	                    int fieldId = rs.getInt("field_id");
	                    String fieldValue = rs.getString("field_value");
	                    
	                    Form form = new Form(formVersion, formName, fieldId, fieldValue);
	  
	                    forms.add(form);
	                }
	                statement.close();
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }

	            return forms;
	        } catch (Exception e) {
	            e.printStackTrace();
	           return null;
	       }
	    }
	 
	 
	 //Get all forms or only one form based on if mode is specific and on form name
	 public static List<Field> field_list(String form_Name, String mode) {
	        try{
	            Connection connection = getConnection();

	            List<Field> fields = new ArrayList<Field>();
	            

	            try {
	            	PreparedStatement statement;
	            	if(mode == "specific") {
		                statement = connection.prepareStatement("SELECT * FROM template_fields WHERE form_name = '"+form_Name+"'");
	            	}
	            	else {
	            		statement = connection.prepareStatement("SELECT * form_name FROM template_fields");
	            	}
	            	ResultSet rs = statement.executeQuery();

	                while (rs.next()) {
	                    int fieldId = rs.getInt("field_id");
	                    String fieldName = rs.getString("field_name");
	                    String formName = rs.getString("form_name");
	                    String fieldType = rs.getString("field_type");
	                    String fieldValue = rs.getString("field_value");
	                    String fieldMandatory = rs.getString("field_mandatory");
	    	            PreparedStatement fieldstmt = connection.prepareStatement("SELECT * FROM radio_fields WHERE field_id = '"+fieldId+"' AND form_name = '"+form_Name+"'");
	                    ResultSet fieldrs = fieldstmt.executeQuery();
	                    List<String> itemList = new ArrayList<String>();
	                    while(fieldrs.next()) {
	                    	itemList.add(fieldrs.getString("radio_value"));
	                    	System.out.println("fieldID" + fieldId + ": " + fieldrs.getString("radio_value"));
	                    }
	                    String[] fieldRadios = new String[itemList.size()];
	                    fieldRadios = itemList.toArray(fieldRadios);
	                    fieldstmt.close();

	                    
	                    Field field = new Field(formName, fieldId, fieldName, fieldType, fieldValue, fieldMandatory, fieldRadios);
	                    System.out.println(fieldRadios);
	                    System.out.println("MADA: " + field.getTheMandatory());
	                    System.out.println("field:" + field.getTheName());
	  
	                    fields.add(field);
	                }
	                statement.close();
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }

	            return fields;
	        } catch (Exception e) {
	            e.printStackTrace();
	           return null;
	       }
	    }
		

	
}