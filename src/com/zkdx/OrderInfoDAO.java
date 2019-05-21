package com.zkdx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderInfoDAO {
    String url = "jdbc:mysql://localhost:3306/shopdemo?" + "useUnicode=true&characterEncoding=utf8&"
        + "serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";

    String SQLusername = "root";
    String SQLpassword = "249658364";
    Connection con = null;

    PreparedStatement ps = null;
    ResultSet rs = null;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public void getConnection() throws SQLException {
        DriverManager.getConnection(url, SQLusername, SQLpassword);
        con = DriverManager.getConnection(url, SQLusername, SQLpassword);
    }

    public void closeAll() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }

    }

    public List<OrderInfo> listOrdersByUsername(String name) {

        List<OrderInfo> list = new ArrayList<OrderInfo>();
        String sql = "select *from orderinfo where username=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String orderid = rs.getString("orderid");
                java.sql.Date orderdatetime = rs.getDate("orderdatetime");
                String productname = rs.getString("productname");
                int productNumber = rs.getInt("productnumber");
                // should be named "productQuantity"
                int price = rs.getInt("price");
                int buyingPrice = rs.getInt("buyingprice");
                String productCategory = rs.getString("productcategory");
                OrderInfo info = new OrderInfo(username, orderid, orderdatetime, productname, productNumber, price,
                    buyingPrice, productCategory);
                list.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public List<OrderInfo> listOrdersByTime(java.sql.Date beginDate, java.sql.Date endDate) {

        List<OrderInfo> list = new ArrayList<OrderInfo>();
        String sql = "select *from orderinfo where orderdatetime between ? and ? ";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(beginDate.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String orderid = rs.getString("orderid");
                java.sql.Date orderdatetime = rs.getDate("orderdatetime");
                String productname = rs.getString("productname");
                int productNumber = rs.getInt("productnumber");
                // should be named "productQuantity"
                int price = rs.getInt("price");
                int buyingPrice = rs.getInt("buyingprice");
                String productCategory = rs.getString("productcategory");
                OrderInfo info = new OrderInfo(username, orderid, orderdatetime, productname, productNumber, price,
                    buyingPrice, productCategory);
                list.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }
    
    
    public List<OrderInfo> listOrdersByIndice(int beginPageIndex,int recordPerPage) {

        List<OrderInfo> list = new ArrayList<OrderInfo>();
        String sql = "select *from orderinfo limit ? , ? ";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, beginPageIndex* recordPerPage);
            ps.setInt(2,  recordPerPage);
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String orderid = rs.getString("orderid");
                java.sql.Date orderdatetime = rs.getDate("orderdatetime");
                String productname = rs.getString("productname");
                int productNumber = rs.getInt("productnumber");
                // should be named "productQuantity"
                int price = rs.getInt("price");
                int buyingPrice = rs.getInt("buyingprice");
                String productCategory = rs.getString("productcategory");
                OrderInfo info = new OrderInfo(username, orderid, orderdatetime, productname, productNumber, price,
                    buyingPrice, productCategory);
                list.add(info);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public TreeMap<java.sql.Date, LinkedList<OrderInfo>> mapOrdersByUsername(String name) {

        TreeMap<java.sql.Date, LinkedList<OrderInfo>> map =
            new TreeMap<java.sql.Date, LinkedList<OrderInfo>>((a, b) -> b.compareTo(a));
        String sql = "select *from orderinfo where username=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String orderid = rs.getString("orderid");
                java.sql.Date orderdatetime = new java.sql.Date(rs.getTimestamp("orderdatetime").getTime());
                String productname = rs.getString("productname");
                int productNumber = rs.getInt("productnumber");
                int price = rs.getInt("price");
                int buyingPrice = rs.getInt("buyingprice");
                String productCategory = rs.getString("productcategory");
                OrderInfo info = new OrderInfo(username, orderid, orderdatetime, productname, productNumber, price,
                    buyingPrice, productCategory);
                if (!map.containsKey(info.getDate())) {
                    LinkedList<OrderInfo> tempList = new LinkedList<OrderInfo>();
                    tempList.add(info);
                    map.put(info.getDate(), tempList);

                } else {
                    map.get(info.getDate()).add(info);
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return map;
    }

    public int insertNewOrderInfo(OrderInfo info) {
        int ans = 0;

        String sql =
            "insert into orderinfo(username, orderid, orderdatetime, productname, productnumber, price,buyingprice,productcategory)"
                + " values(?,?,?,?,?,?,?,?)";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, info.getUsername());
            ps.setString(2, info.getOrderid());
            ps.setTimestamp(3, new java.sql.Timestamp(info.getDate().getTime()));
            ps.setString(4, info.getProductname());
            ps.setInt(5, info.getProductNumber());
            ps.setInt(6, info.getPrice());
            ps.setInt(7, info.getBuyingPrice());
            ps.setString(8, info.getProductCategory());
            ans = ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
    }
    
    
    public int getTotalOrderQuantity( ) {
        int ans = 0;

        String sql =
            "select count(orderid) as totalquantity from orderinfo";
        try {
            getConnection();
            ps = con.prepareStatement(sql);          
            rs = ps.executeQuery();
            while (rs.next()) {
                
                ans=rs.getInt("totalquantity");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        java.sql.Date beginDate = null, endDate = null;

        try {
            beginDate = new java.sql.Date(format.parse("2008-01-01").getTime());
            endDate = new java.sql.Date(format.parse("2012-01-01").getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        OrderInfoDAO dao = new OrderInfoDAO();
        /*
       for(int i=0;i<100;i++) {
           OrderInfo info=new    OrderInfo( "测试用户"+i, ""+ i,new Date (System.currentTimeMillis()-i*86400L* 1000L),  "product"+i,  i,  100+i,
                100,  "测试类别") ;                         
           dao.insertNewOrderInfo(info);
       }
       */
        int totalOrderQuantity=dao.getTotalOrderQuantity();
        System.out.println(totalOrderQuantity);
        List<OrderInfo>list=dao.listOrdersByIndice(2, 10);
        for(OrderInfo info:list)
            System.out.println(info);
    }
}
