package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteCategory
 */
@WebServlet("/DeleteCategory")
public class DeleteCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Employee employee = (Employee)request.getSession().getAttribute("employee");

        if (employee == null || !"采购员".equals(employee.getJob())) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        String categoryNameToDel = request.getParameter("categorynametodel");
        System.out.println(categoryNameToDel);
        CategoryDAO dao = new CategoryDAO();
        dao.deleteCategoryAndItsSubCategoriesByName(categoryNameToDel);
        response.sendRedirect(request.getContextPath() + "/DeleteCategory.jsp");

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
