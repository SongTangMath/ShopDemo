 package com.zkdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class OrderInfoDAO {
     String url = "jdbc:mysql://localhost:3306/shopdemo?"
         + "useUnicode=true&characterEncoding=utf8&"
         + "serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";

     String SQLusername = "root";
     String SQLpassword = "249658364";
     Connection con = null;
 
     PreparedStatement ps = null;
     ResultSet rs =null;
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
     if( rs != null)
         try {
             rs.close();
         } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     if(ps != null)
         try {
             ps.close();
         } catch (SQLException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
         }
     if(con != null)
         try {
             con.close();
         } catch (SQLException e2) {
             // TODO Auto-generated catch block
             e2.printStackTrace();
         }
     
 }
         public List<OrderInfo>listOrdersByUsername(String name) {
             
             List<OrderInfo> list=new ArrayList<OrderInfo>();
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
                    int productNumber=rs.getInt("producftnumber");
                    //should be named "productQuantity"
                    int price = rs.getInt("price");
                    OrderInfo info =
                        new OrderInfo(username, orderid, orderdatetime, productname,productNumber, price);
                    list.add(info);
                }
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();
            }
             finally {
                 closeAll();
             }
             return list;
         }
         
 public TreeMap<java.sql.Date,LinkedList<OrderInfo>> mapOrdersByUsername(String name) {
             
     TreeMap<java.sql.Date,LinkedList<OrderInfo>>map = 
         new  TreeMap<java.sql.Date,LinkedList<OrderInfo>>((a,b)->b.compareTo(a));
             String sql = "select *from orderinfo where username=?";
             try {
                 getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String orderid = rs.getString("orderid");
                    java.sql.Date orderdatetime =new java.sql.Date(rs.getTimestamp("orderdatetime").getTime());
                    String productname = rs.getString("productname");
                    int productNumber=rs.getInt("productnumber");
                    int price = rs.getInt("price");
                    OrderInfo info =
                        new OrderInfo(username, orderid, orderdatetime, productname, productNumber, price);
                    if(!map.containsKey(info.getDate())){
                        LinkedList<OrderInfo> tempList=new LinkedList<OrderInfo>();
                        tempList.add(info);
                        map.put(info.getDate(),tempList);
                        
                    }
                    else {
                        map.get(info.getDate()).add(info);
                    }
                }
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();
            }
             finally {
                 closeAll();
             }
            
             return map;
         }
         public int insertNewOrderInfo(OrderInfo info) {
             int ans=0;
             
             String sql = "insert into orderinfo(username, orderid, orderdatetime, productname, productnumber, price)"
                 + " values(?,?,?,?,?,?)";
             try {
                 getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1, info.getUsername());
                ps.setString(2, info.getOrderid());
                ps.setTimestamp(3, new java.sql.Timestamp(info.getDate().getTime()));
                ps.setString(4,info.getProductname());
                ps.setInt(5, info.getProductNumber());
                ps.setInt(6, info.getPrice());
                ans=ps.executeUpdate();
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();
            }
             finally {
                 closeAll();
             }
             return ans;
         }
         public static void main(String[]args) {
           
         }
}