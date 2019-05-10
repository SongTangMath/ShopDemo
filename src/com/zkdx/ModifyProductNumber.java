package com.zkdx;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifyProductNumber
 */
@WebServlet("/ModifyProductNumber")
public class ModifyProductNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyProductNumber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Employee employee=(Employee)request.getSession().getAttribute("employee");
		
		if(employee==null){
			response.sendRedirect(request.getContextPath()+"/index.html");  
		return;}
		HashMap<Integer,String>mapAdd=new HashMap<Integer,String>();
		HashMap<Integer,String>mapSub=new HashMap<Integer,String>();
		ProductDAO dao=new ProductDAO();
		List<Product>list=dao.getAllProducts();
		boolean isPurchaser=false;
		for(Product temp:list) {
			mapAdd.put(temp.getId(), request.getParameter("addProductID"+temp.getId()));
			mapSub.put(temp.getId(), request.getParameter("subProductID"+temp.getId()));
		}
		for(int key:mapAdd.keySet()) {
			if(mapAdd.get(key)==null||"".equals(mapAdd.get(key)))continue;
			isPurchaser=true;
			System.out.println("ProductID: "+key+" add"+mapAdd.get(key));
			try {
				int num=Integer.parseInt(mapAdd.get(key));
				if(num<0)num=0;
				dao.modifyProductIntentoryQuantityByProductId(key, num);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		for(int key:mapSub.keySet()) {
			if(mapSub.get(key)==null||"".equals(mapSub.get(key)))continue;
			System.out.println("ProductID: "+key+" sub"+mapSub.get(key));
			try {
				int num=Integer.parseInt(mapSub.get(key));
				if(num<0)num=0;
				dao.modifyProductIntentoryQuantityByProductId(key, -num);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
			
		
		if(isPurchaser)
		response.sendRedirect(request.getContextPath()+"/Purchaser.jsp"); 
		else response.sendRedirect(request.getContextPath()+"/Seller.jsp"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
