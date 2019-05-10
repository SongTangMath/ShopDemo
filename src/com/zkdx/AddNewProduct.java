package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zkdx.*;
/**
 * Servlet implementation class AddNewProduct
 */
@WebServlet("/AddNewProduct")
public class AddNewProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		Employee employee=(Employee)request.getSession().getAttribute("employee");
		
		if(employee==null||!"采购员".equals(employee.getJob())){
			response.sendRedirect(request.getContextPath()+"/index.html");  
		return;}
		
		String productName=request.getParameter("productname");
		int productPrice = 0;
		try {
		 productPrice=Integer.parseInt(request.getParameter("productprice"));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if(productPrice < 0)
			productPrice = 0;
		ProductDAO dao=new ProductDAO();
		Product product=dao.getProductByProductName(productName);
		if(product==null) {
			dao.insertNewProduct(productName, "", 0, productPrice, "");
		}
		response.sendRedirect(request.getContextPath()+"/Purchaser.jsp"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
