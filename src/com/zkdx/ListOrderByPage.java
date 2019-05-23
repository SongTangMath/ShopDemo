package com.zkdx;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Servlet implementation class ListOrderByPage
 */
@WebServlet("/ListOrderByPage")
public class ListOrderByPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListOrderByPage() {
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
        String orderListIndexString = request.getParameter("orderlistindex");
        int orderListIndex = -1;
        try {
            orderListIndex = Integer.parseInt(orderListIndexString);
        } catch (NumberFormatException e) {

        }
        if (orderListIndex <= 0) {
            orderListIndex = 1;
        }
        OrderInfoDAO dao = new OrderInfoDAO();
        System.out.println("orderListIndex: " + orderListIndex);
        List<OrderInfo> list = dao.listOrdersByIndice(orderListIndex - 1, 10);
        request.setAttribute("orderInfoList", list);
        request.getRequestDispatcher("ShowAllOrderInfo.jsp").forward(request, response);
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
