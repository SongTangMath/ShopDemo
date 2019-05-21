package com.zkdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtendedAttributeDAO {
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

    public List<ExtendedAttribute> listAttributesByProductName(String name) {

        List<ExtendedAttribute> list = new ArrayList<ExtendedAttribute>();
        String sql = "select *from extendedattribute where product_name=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                int attributeID = rs.getInt("attribute_id");
                int productID = rs.getInt("product_id");
                String productname = rs.getString("product_name");
                String attributeName = rs.getString("attribute_name");;
                String attributeValue = rs.getString("attribute_value");;
                ExtendedAttribute attr =
                    new ExtendedAttribute(productID, productname, attributeName, attributeValue, attributeID);
                list.add(attr);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public List<ExtendedAttribute> listAttributesByProductID(int id) {

        List<ExtendedAttribute> list = new ArrayList<ExtendedAttribute>();
        String sql = "select *from extendedattribute where product_id=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int attributeID = rs.getInt("attribute_id");
                int productID = rs.getInt("product_id");
                String productname = rs.getString("product_name");
                String attributeName = rs.getString("attribute_name");;
                String attributeValue = rs.getString("attribute_value");;
                ExtendedAttribute attr =
                    new ExtendedAttribute(productID, productname, attributeName, attributeValue, attributeID);
                System.out.println(attr);
                list.add(attr);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public int insertNewExtendedAttribute(int product_id, String product_name, String attribute_name,
        String attribute_value) {
        int ans = 0;
        String sql =
            "insert into extendedattribute(product_id,product_name ,attribute_name, attribute_value) values(?,?,?,?)";

        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, product_id);
            ps.setString(2, product_name);
            ps.setString(3, attribute_name);
            ps.setString(4, attribute_value);
            ans = ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
    }
    public int deleteExtendedAttributeByID(int id) {
        int ans = 0;
        String sql =
            "delete from extendedattribute where attribute_id=?";

        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ans = ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
        
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
