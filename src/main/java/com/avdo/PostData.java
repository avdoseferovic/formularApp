package com.avdo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class PostData
 */
public class PostData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostData() {
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

		
//		Field field = this.gson.fromJson(request.getReader(), Field.class);
		Field[] fields = this.gson.fromJson(request.getReader(), Field[].class);
		
		String fieldJsonString = this.gson.toJson(fields);
		for(int i = 0; i < fields.length; i++) {
			Dbconnect.insertField(fields[i]);
			Dbconnect.insertRadios(fields[i]);
		}
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(fieldJsonString);
        out.flush();
	}

}
