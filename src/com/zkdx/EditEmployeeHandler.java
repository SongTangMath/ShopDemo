package com.zkdx;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditEmployeeHandler
 */
@WebServlet("/EditEmployeeHandler")
public class EditEmployeeHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEmployeeHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		String username = (String)request.getSession().getAttribute("adminName");
		System.out.println(username);
		int ans = -1;
		if (username == null || !username.equals("admin")) {
			response.sendRedirect(request.getContextPath()+"/index.html");  
		return;
		}
		String idString = request.getParameter("EmployeeId");
		String method = request.getParameter("Method");
		
		System.out.println(method);
		
		String basePath = request.getScheme() + "://"
	            + request.getServerName() + ":" + request.getServerPort()
	            + request.getContextPath() + "/";
		
		if ("delete".equals(method))
		{
			EmployeeDAO dao=new EmployeeDAO();
			try {
				System.out.println("delete employee");
				int id = Integer.parseInt(idString);
				
			Employee employee = dao.getEmployeeById(id);		
			if (employee == null) {
				ans = 0;
				return;
			}
			ans = dao.deleteEmployeeById(id);	
			System.out.println(ans);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("status", ans);
			RequestDispatcher disp=request.getRequestDispatcher("/Manager.jsp");			
			disp.forward(request, response);
		}
			
		else if ("modify".equals(method)) {
			EmployeeDAO dao=new EmployeeDAO();
			try{				
				int id = Integer.parseInt(idString);
				String name = request.getParameter("employeeName");
				String password = request.getParameter("employeePassword");
				String departmentName = request.getParameter("employeedepartmentName");
				String job = request.getParameter("employeeJob");
				int salary = Integer.parseInt(request.getParameter("employeeSalary"));
			Employee employee = dao.getEmployeeById(id);
			ans = dao.modifyEmployeeById(id, name, password, departmentName, job, salary);
			/*parseInt may throw an java.lang.NumberFormatException  */
			if (employee == null) {
				ans = 0;
				return;
			}
			else request.getSession().setAttribute("employee", employee);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("status", ans);
			RequestDispatcher disp = request.getRequestDispatcher("/Manager.jsp");			
			disp.forward(request, response);
		}
		else if ("create".equals(method)) {
			EmployeeDAO dao = new EmployeeDAO();			
							
				int id = 1;
				try {
					id = Integer.parseInt(idString);
				}
				catch(NumberFormatException e) {
					System.out.println("NumberFormatException!");
				}
				System.out.println(id);
				Employee employee = dao.getEmployeeById(id);
				System.out.println(employee);
				if(employee == null) {
				String name = request.getParameter("employeeName");
				String password = request.getParameter("employeePassword");
				String departmentName = request.getParameter("employeedepartmentName");
				String job = request.getParameter("employeeJob");
				
				boolean isArgsValid = true;
				if (name == null || "".equals(name)) {
					isArgsValid = false;
				}
				if (password == null || "".equals(password)) {
					isArgsValid = false;
				}
				if (departmentName == null || "".equals(departmentName)) {
					isArgsValid = false;
				}
				if (job == null || "".equals(job)) {
					isArgsValid = false;
				}
				int salary = 0; 
				try {
					salary = Integer.parseInt(request.getParameter("employeeSalary"));
				}
				catch(NumberFormatException e) {
					e.printStackTrace();
				}
				 employee = new  Employee(id,name,password,departmentName,job,salary);
					System.out.println("create new "+ employee.toString());
					
					if (isArgsValid) {
						ans = dao.insertNewEmployee(id, name, password, departmentName, job, salary);
					}				
				else {
					ans = 0;
				}
			}
			
			request.setAttribute("status", ans);
			System.out.println("status: " + ans);
			request.getRequestDispatcher("/Manager.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
