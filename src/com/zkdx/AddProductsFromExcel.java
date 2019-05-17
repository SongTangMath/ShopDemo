package com.zkdx;
import java.util.*;
import java.io.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.ss.usermodel.*;
/**
 * Servlet implementation class AddProductsFromExcel
 */
@WebServlet("/AddProductsFromExcel")
public class AddProductsFromExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductsFromExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	        request.setCharacterEncoding("utf-8");
	        FileItemFactory fileItemFactory = new DiskFileItemFactory();
	        ServletFileUpload sfu = new ServletFileUpload(fileItemFactory);
	        sfu.setHeaderEncoding("utf-8");
	        List<FileItem> items = new ArrayList<FileItem>();
	        String productname = null;
	        try {
	            items = sfu.parseRequest(request);
	            System.out.println("items.size=" + items.size());
	        } catch (FileUploadException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	            + request.getContextPath() + "/";

	        ProductDAO dao = new ProductDAO();
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String itemFieldName = item.getFieldName();
	                String value = null;
	                value = item.getString("utf-8");
	                if ("productname".equals(itemFieldName)) {
	                    productname = value;
	                }

	            }
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
