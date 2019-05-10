package com.zkdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterHandler
 */
@WebServlet("/RegisterHandler")
public class RegisterHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterHandler() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String original = request.getParameter("username");
		String username = new String(original.getBytes("ISO8859-1"),"UTF-8");
		User user = new UserDAO().getUserByUsername(username);
		System.out.println(user);
		if (user != null) {
			response.getWriter().append("true");
		}
		else {
			response.getWriter().append("false");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		boolean isArgsValid = true;
		if (username == null || "".equals(username)) {
			isArgsValid = false;
		}
		if (password == null || "".equals(password)) {
			isArgsValid = false;
		}
		if (phone == null || "".equals(phone)) {
			isArgsValid = false;
		}
		if (address == null || "".equals(address)) {
			isArgsValid = false;
		}
		String registerResult="false";
		UserDAO dao=new UserDAO();
		if (isArgsValid && dao.getUserByUsername(username) == null) {
			dao.insertNewUser(username, password, phone, address);
			registerResult="true";
		}
		request.setAttribute("registerResult", registerResult);
		request.getRequestDispatcher("/RegisterResult.jsp").forward(request, response);
	}

}
