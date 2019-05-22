package com.zkdx;

import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserBuyHandler
 */
@WebServlet("/UserBuyHandler")
public class UserBuyHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBuyHandler() {
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
        User user = (User)request.getSession().getAttribute("user");
        System.out.println(user);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        OrderInfoDAO orderInfoDao = new OrderInfoDAO();
        ProductDAO dao = new ProductDAO();
        ExtendedAttributeDAO dao1 = new ExtendedAttributeDAO();
        List<Product> list = dao.getAllProducts();
        long timeLong = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(timeLong);
        for (Product temp : list) {
            String key = request.getParameter("buyProductID" + temp.getId());
            if (key == null || "".contentEquals(key)) {
                continue;
            }
            int num = 0;

            try {
                num = Integer.parseInt(key);
                if (num > temp.getInventoryQuantity()) {
                    num = temp.getInventoryQuantity();
                }
                if (num <= 0) {
                    continue;
                }
                List<ExtendedAttribute> list1 = dao1.listAttributesByProductID(temp.getId());
                String extendedAttributeString = "";
                for (ExtendedAttribute attr : list1) {
                    String parameterName="ProductID" + temp.getId() + " " + attr.getAttributeName();
                    String attrValue = request.getParameter(parameterName);
                    System.out.println(parameterName);
                    System.out.println(attrValue);
                    if (attrValue != null) {
                        
                        extendedAttributeString += (attr.getAttributeName() + " "+attrValue+" ");
                    }

                }
                OrderInfo info =
                    new OrderInfo(user.getUsername(), user.getUsername() + timeLong, date, temp.getProductname(), num,
                        temp.getPrice(), temp.getBuyingPrice(), temp.getProductCategory(), extendedAttributeString);
                System.out.println(info);
                orderInfoDao.insertNewOrderInfo(info);
                dao.modifyProductIntentoryQuantityByProductId(temp.getId(), -num);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // response.sendRedirect(request.getContextPath() + "/Products.jsp");
        request.getRequestDispatcher("/Products.jsp").forward(request, response);

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
