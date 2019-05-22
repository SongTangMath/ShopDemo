package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        Employee employee = (Employee)request.getSession().getAttribute("employee");

        if (employee == null || !"采购员".equals(employee.getJob())) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        String productName = request.getParameter("productname");

        String level0 = request.getParameter("level0");
        String level1 = request.getParameter("level1");
        String level2 = request.getParameter("level2");
        if (level0 == null || "".equals(level0) || level1 == null || "".equals(level1) || level2 == null
            || "".equals(level2)) {
            response.sendRedirect(request.getContextPath() + "/Purchaser.jsp");
        }
        if (level0.contains("请选择大类")) {
            level0 = "";
        }
        if (level1.contains("请选择第二层分类")) {
            level1 = "";
        }
        if (level2.contains("请选择第三层分类")) {
            level2 = "";
        }
        String productCategory = level0 +" "+ level1+" " + level2;
        int productPrice = -1;
        int buyingPrice = -1;
        try {
            String temp = request.getParameter("productprice");
            if (temp != null) {
                productPrice = Integer.parseInt(temp);
            }
            temp = request.getParameter("buyingprice");
            if (temp != null) {
                buyingPrice = Integer.parseInt(temp);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (productPrice > 0 && buyingPrice > 0 && productName != null && !"".equals(productName)) {
            ProductDAO dao = new ProductDAO();
            Product product = dao.getProductByProductName(productName);
            if (product == null) {
                dao.insertNewProduct(productName, "", 0, productPrice, "", buyingPrice, productCategory);
            }
        }
        response.sendRedirect(request.getContextPath() + "/Purchaser.jsp");
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
