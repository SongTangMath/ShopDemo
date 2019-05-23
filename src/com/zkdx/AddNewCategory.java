package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddNewCategory
 */
@WebServlet("/AddNewCategory")
public class AddNewCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        Employee employee = (Employee)request.getSession().getAttribute("employee");

        if (employee == null || !"采购员".equals(employee.getJob())) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        String parentCategoryName = request.getParameter("parentcategoryname");
        String CategoryName = request.getParameter("categoryname");
        String isTop = request.getParameter("isTop");
        System.out.println("parentCategoryName "+parentCategoryName);
        System.out.println("CategoryName "+CategoryName);
        System.out.println("isTop "+isTop);
        CategoryDAO dao = new CategoryDAO();
        if ("1".equals(isTop)) {

            dao.insertNewCategory(CategoryName, "", 0, 0);
        } else if ("0".equals(isTop)) {
            Category parent = dao.getCategoryByName(parentCategoryName);
            if (parent != null) {
                    dao.insertNewCategory(CategoryName, parentCategoryName, 0, parent.getCategoryLevel()+1);
            }

        }
        response.sendRedirect(request.getContextPath() + "/Purchaser.jsp");
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
