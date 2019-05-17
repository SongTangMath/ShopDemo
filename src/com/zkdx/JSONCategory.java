package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import java.util.*;
/**
 * Servlet implementation class JSONCategory
 */
@WebServlet("/JSONCategory")
public class JSONCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html;charset=utf-8");
        
        List<Category> listLevel0Categories = new CategoryDAO().listLevel0Categories();
        /*
        List<String>list=new ArrayList<String>();        
        for(Category category:listLevel0Categories) {
            list.add(category.getCategoryName());
        }
        */
        String str = JSONArray.fromObject(listLevel0Categories).toString();
        response.getWriter().write(str);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String level0=request.getParameter("level0");
		String level1=request.getParameter("level1");

		List<Category> listLevel1Categories = null;
		List<Category> listLevel2Categories = null;
		String str="";
		if(level0!=null && !"".equals(level0)) {
		    listLevel1Categories = new CategoryDAO().listCategoriesByParentName(level0);
		    str = JSONArray.fromObject(listLevel1Categories).toString();
		}
		else if(level1!=null && !"".equals(level1)) {
            listLevel2Categories = new CategoryDAO().listCategoriesByParentName(level1);
            str = JSONArray.fromObject(listLevel2Categories).toString();
        }
		 
	        response.getWriter().write(str);
	}

}
