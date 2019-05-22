package com.zkdx;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserQueryHandler
 */
@WebServlet("/UserQueryHandler")
public class UserQueryHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserQueryHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        String queryString = request.getParameter("productcategory");
        String level0= request.getParameter("level0");
        String level1= request.getParameter("level1");
        String level2= request.getParameter("level2");
        System.out.println(level0);
        System.out.println(level1);
        System.out.println(level2);
        List<Product> list = new ArrayList<Product> (); 
        ProductDAO dao=new ProductDAO();
        if (queryString != null && !"".equals(queryString)) {
           list =dao.listProductsByProductCategory(queryString);
           
        } else if(level2!=null || level1!=null || level0 != null){
           
         list = dao.listProductsByProductCategory(level2);
         if(list.isEmpty()) {
             list = dao.listProductsByProductCategory(level1);
             if(list.isEmpty()) {
                 list = dao.listProductsByProductCategory(level0);
             }
         }
            
        }
        request.getSession().setAttribute("productList", list);
        request.getRequestDispatcher("Products.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
