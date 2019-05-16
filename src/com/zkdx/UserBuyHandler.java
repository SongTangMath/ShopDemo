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
        User user = (User)request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        OrderInfoDAO orderInfoDao = new OrderInfoDAO();
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getAllProducts();
        long timeLong = System.currentTimeMillis();
        System.out.println(timeLong);
        java.sql.Date date = new java.sql.Date(timeLong);
        DateFormat ddtf = DateFormat.getDateTimeInstance();
        System.out.println(ddtf.format(date));

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
                OrderInfo info = new OrderInfo(user.getUsername(), user.getUsername() + timeLong, date,
                    temp.getProductname(), num, temp.getPrice(),temp.getBuyingPrice(),temp.getProductCategory());
                System.out.println(info);
                orderInfoDao.insertNewOrderInfo(info);
                dao.modifyProductIntentoryQuantityByProductId(temp.getId(), -num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/Products.jsp");

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
