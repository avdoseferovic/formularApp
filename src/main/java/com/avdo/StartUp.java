package com.avdo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartUp implements ServletContextListener {

	  public void contextDestroyed(ServletContextEvent arg0) {
	    //Notification that the servlet context is about to be shut down.   
	  }

	  public void contextInitialized(ServletContextEvent arg0) {
	    // do all the tasks that you need to perform just after the server starts
		  
		  //We create tables here so that they're created only once (at startup)
		  Dbconnect.createTables();
		  //Dbconnect.insertData();
		  Form form = new Form ("1", "avdotest", 5, "fieldValue");
		  Form form2 = new Form ("1", "avdotest2", 1, "fieldValue2");
		  Dbconnect.insertForm(form);
		  Dbconnect.insertForm(form2);

	    //Notification that the web application initialization process is starting
	  }

	}