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
 * Servlet implementation class JSONExtendedAttributeMap
 */
@WebServlet("/JSONExtendedAttributeMap")
public class JSONExtendedAttributeMap extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONExtendedAttributeMap() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String buyProductID = request.getParameter("buyProductID");
        
        HashMap<String, ArrayList<String>> attributeValueMap = new HashMap<String, ArrayList<String>>();
        try {
            attributeValueMap = new ExtendedAttributeMap(Integer.parseInt(buyProductID)).getAttributeValueMap();
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
        }
        /*
        List<String>list=new ArrayList<String>();        
        for(Category category:listLevel0Categories) {
            list.add(category.getCategoryName());
        }
        */
        String str = JSONArray.fromObject(attributeValueMap).toString();
        response.getWriter().write(str);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
