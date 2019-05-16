package com.zkdx;

import java.util.*;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.streaming.*;

/**
 * Servlet implementation class QuerySellingStatus
 */
@WebServlet("/QuerySellingStatus")
public class QuerySellingStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuerySellingStatus() {
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
        String username = (String)request.getSession().getAttribute("adminName");

        if (username == null || !username.equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        request.setCharacterEncoding("utf-8");
        String beginDateString = request.getParameter("begindate");
        String endDateString = request.getParameter("enddate");
        System.out.println(beginDateString);
        System.out.println(endDateString);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date beginDate = null, endDate = null;
        try {
            beginDate = simpleDateFormat.parse(beginDateString);
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (beginDate == null || endDate == null) {
            response.sendRedirect(request.getContextPath() + "/Manager.jsp");
        }
        OrderInfoDAO dao = new OrderInfoDAO();
        List<OrderInfo> list =
            dao.listOrdersByTime(new java.sql.Date(beginDate.getTime()), new java.sql.Date(endDate.getTime()));
        Map<String, SellingStatus> sellingStatusMap = new HashMap<String, SellingStatus>();
        for (OrderInfo info : list) {
            int totalCost = info.getBuyingPrice() * info.getProductNumber();
            int totalProfit = (info.getPrice() - info.getBuyingPrice()) * info.getProductNumber();
            if (!sellingStatusMap.containsKey(info.getProductname())) {

                sellingStatusMap.put(info.getProductname(),
                    new SellingStatus(info.getProductname(), info.getProductNumber(), totalCost, totalProfit));

            } else {
                SellingStatus status = sellingStatusMap.get(info.getProductname());
                status.addQuantitySold(info.getProductNumber());
                status.addTotalCost(totalCost);
                status.addTotalProfit(totalProfit);
            }
        }
        List<SellingStatus> sellingStatusList = new ArrayList<SellingStatus>();
        for (String key : sellingStatusMap.keySet()) {
            sellingStatusList.add(sellingStatusMap.get(key));
        }
        Collections.sort(sellingStatusList, (a, b) -> b.getQuantitySold() - a.getQuantitySold());
        // for(SellingStatus status:sellingStatusList) response.getWriter().append(status.toString()+"\n");
        Workbook workBook = new SXSSFWorkbook();
        Sheet sheet = workBook.createSheet("账户表数据");

        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 35 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("销量排名");
        row.createCell(1).setCellValue("商品名称");

        row.createCell(2).setCellValue("销售数量");
        row.createCell(3).setCellValue("该商品总成本");
        row.createCell(4).setCellValue("该商品总利润");
        int totalCost = 0, totalProfit = 0;
        for (int i = 0; i < sellingStatusList.size(); i++) {
            SellingStatus status = sellingStatusList.get(i);
            row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(status.getProductName());

            row.createCell(2).setCellValue(status.getQuantitySold());
            row.createCell(3).setCellValue(status.getTotalCost());
            row.createCell(4).setCellValue(status.getTotalProfit());
            totalCost += status.getTotalCost();
            totalProfit += status.getTotalProfit();
        }
        row = sheet.createRow(sellingStatusList.size() + 2);
        row.createCell(0).setCellValue("以上商品总成本");
        row.createCell(1).setCellValue("以上商品总利润");

        row = sheet.createRow(sellingStatusList.size() + 3);
        row.createCell(0).setCellValue(totalCost);
        row.createCell(1).setCellValue(totalProfit);

        String fileName = "" + beginDateString + "到" + beginDateString + "销售数据.xlsx";
        response.setHeader("Content-disposition",
            "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1")); // 设置文件头编码格式

        response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");// 设置类型

        response.setHeader("Cache-Control", "no-cache");// 设置头

        response.setDateHeader("Expires", 0);// 设置日期头
        workBook.write(response.getOutputStream());
        workBook.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

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
