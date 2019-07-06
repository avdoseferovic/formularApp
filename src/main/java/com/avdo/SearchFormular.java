package com.avdo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SearchFormular
 */
public class SearchFormular extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFormular() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private Gson gson = new Gson();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String formName = this.gson.fromJson(request.getReader(), String.class);
		
		List<Field> fields = Dbconnect.field_list(formName, "specific");
		
		String fieldJsonString = new String("");
		
		if(fields.isEmpty()) {
			System.out.println("Is empty:");
			fieldJsonString = this.gson.toJson("created");
		}
		else {
			fieldJsonString = this.gson.toJson(fields);
		}
		
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(fieldJsonString);
        out.flush();
	}

}
