package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifyProductExtendedAttributeHandler
 */
@WebServlet("/ModifyProductExtendedAttributeHandler")
public class ModifyProductExtendedAttributeHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyProductExtendedAttributeHandler() {
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
        String attributeID = request.getParameter("attributeid");
        String method = request.getParameter("method");
        ExtendedAttributeDAO dao = new ExtendedAttributeDAO();
        if ("delete".equals(method)) {
            System.out.println("delete attributeID " + attributeID);
            dao.deleteExtendedAttributeByID(Integer.parseInt(attributeID));
        }
        request.getRequestDispatcher("/ModifyProductExtendedAttribute.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String method = request.getParameter("method");

        if ("add".equals(method)) {
            String ProductID = request.getParameter("ProductID");
            String attributeName = request.getParameter("attributename");
            String attributeValue = request.getParameter("attributevalue");
            System.out.println("add attributeName " + attributeName + " attributeValue " + attributeValue);
            ProductDAO dao1 = new ProductDAO();
            Product product = dao1.getProductById(Integer.parseInt(ProductID));
            ExtendedAttributeDAO dao2 = new ExtendedAttributeDAO();
            if (product != null) {
                dao2.insertNewExtendedAttribute(product.getId(), product.getProductname(), attributeName,
                    attributeValue);
            }

        }

        request.getRequestDispatcher("/ModifyProductExtendedAttribute.jsp").forward(request, response);
    }

}
