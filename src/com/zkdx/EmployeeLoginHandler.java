package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmployeeLoginHandler
 */
@WebServlet("/EmployeeLoginHandler")
public class EmployeeLoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeLoginHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		request.setCharacterEncoding("UTF-8");
		String employeeIdString=request.getParameter("employeeId");
		String password=request.getParameter("password");
		EmployeeDAO dao=new EmployeeDAO();
		int employeeId=-1;
		try {
			employeeId=Integer.parseInt(employeeIdString);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Employee employee=dao.getEmployeeById(employeeId);
		System.out.println(employee);
		if(employee==null||!password.equals(employee.getPassword()))
			{
				request.getRequestDispatcher("index.html").forward(request, response);
				return;
			}
		request.getSession().setAttribute("employee", employee);
		if("商品策划".equals(employee.getJob())){
			request.getRequestDispatcher("ProductsPlanner.jsp").forward(request, response);
			return;
		}
		else if("采购员".equals(employee.getJob())){
			request.getRequestDispatcher("Purchaser.jsp").forward(request, response);
			return;
		}
		else	request.getRequestDispatcher("employee.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
