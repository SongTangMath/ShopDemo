package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Servlet implementation class CustomerServiceQueryUser
 */
@WebServlet("/CustomerServiceQueryUser")
public class CustomerServiceQueryUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServiceQueryUser() {
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
        String username = request.getParameter("username");
        request.setAttribute("username", username);
        OrderInfoDAO dao = new OrderInfoDAO();
        int totalOrderQuantity = dao.getTotalOrderQuantity();
        request.getSession().setAttribute("totalOrderQuantity", totalOrderQuantity);
        User user = null;
        TreeMap<java.sql.Date, LinkedList<OrderInfo>> map = new TreeMap<java.sql.Date, LinkedList<OrderInfo>>();
        if (username != null && !"".equals(username)) {
            map = dao.mapOrdersByUsername(username);
            user = new UserDAO().getUserByUsername(username);
            request.setAttribute("user", user);
        }
        request.setAttribute("orderInfoMap", map);
        request.getRequestDispatcher("CustomerService.jsp").forward(request, response);
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
