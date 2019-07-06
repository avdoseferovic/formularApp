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
 * Servlet implementation class PostFormData
 */
public class PostFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostFormData() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private Gson gson = new Gson();


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Form[] frontForms = this.gson.fromJson(request.getReader(), Form[].class);
		
		
		
		for(int i = 0; i < frontForms.length; i++) {
			Dbconnect.insertForm(frontForms[i]);
		}
		
		List<Form> forms = Dbconnect.list();
		
		String formJsonString = this.gson.toJson(forms);
		
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(formJsonString);
        out.flush();
	}

}
