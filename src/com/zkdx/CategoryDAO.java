package com.zkdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoryDAO {
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

    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT* from category where category_id=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {

                int categoryID = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int parentID = rs.getInt("parent_id");
                int isEnd = rs.getInt("is_end");
                int categoryStatus = rs.getInt("category_status");
                int cactegoryLevel = rs.getInt("cactegory_level");
                category = new Category(categoryID, categoryName, parentID, isEnd, categoryStatus, cactegoryLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return category;
    }

    public Category getCategoryByName(String name) {
        Category category = null;
        String sql = "SELECT* from category where category_name=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {

                int categoryID = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int parentID = rs.getInt("parent_id");
                int isEnd = rs.getInt("is_end");
                int categoryStatus = rs.getInt("category_status");
                int cactegoryLevel = rs.getInt("category_level");
                category = new Category(categoryID, categoryName, parentID, isEnd, categoryStatus, cactegoryLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return category;
    }
    public int deleteCategoryAndItsSubCategoriesByName(String name) {
        List<Category> list= listCategoriesByParentName(name);
        int ans=0;
        for(Category category:list) {
            deleteCategoryAndItsSubCategoriesByName(category.getCategoryName());
        }
        String sql="delete from category where category_name=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ans = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return ans;

    }

    

    public int insertNewCategory(String name, String parentName, int categoryStatus, int categoryLevel) {
        int parentID = -1;
        int ans = 0;
        Category test = getCategoryByName(name);
        Category parent = null;
        if (test != null) {
            return 0;
        }
        if (categoryLevel != 0) {
            parent = getCategoryByName(parentName);
            if (parent == null) {
                return 0;
            }

            else {
                parentID = parent.getCategoryID();
                setIsEnd(parent.getCategoryName(), 0);
            }
        }
        String sql =
            "insert into category(category_name,parent_id,is_end,category_status,category_level) values(?,?,?,?,?)";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, parentID);
            ps.setInt(3, 1);
            ps.setInt(4, categoryStatus);
            if (parent != null && 1 + parent.getCategoryLevel() != categoryLevel) {
                System.out.println(name + " 商品分类层次错误");
                categoryLevel = 1 + parent.getCategoryLevel();
            }
            ps.setInt(5, categoryLevel);
            ans = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return ans;

    }

    public int setIsEnd(String name, int isEnd) {
        int ans = 0;
        String sql = "update category set is_end=? where category_name=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, isEnd);
            ps.setString(2, name);
            ans = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
    }

    public int setStatus(String name, int status) {
        int ans = 0;
        String sql = "update category set category_status=? where category_name=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, status);
            ps.setString(2, name);
            ans = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return ans;
    }

    public List<Category> listCategoriesByParentName(String parentName) {
        List<Category> list = new ArrayList<Category>();
        Category parent = getCategoryByName(parentName);
        if (parent == null) {
            return list;
        }
        String sql = "select* from category where parent_id=?";
        try {
            getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, parent.getCategoryID());
            rs = ps.executeQuery();
            while (rs.next()) {

                int categoryID = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int parentID = rs.getInt("parent_id");
                int isEnd = rs.getInt("is_end");
                int categoryStatus = rs.getInt("category_status");
                int cactegoryLevel = rs.getInt("category_level");
                Category category =
                    new Category(categoryID, categoryName, parentID, isEnd, categoryStatus, cactegoryLevel);
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public List<Category> listLevel0Categories() {
        List<Category> list = new ArrayList<Category>();

        String sql = "select* from category where parent_id=-1";
        try {
            getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {

                int categoryID = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int parentID = rs.getInt("parent_id");
                int isEnd = rs.getInt("is_end");
                int categoryStatus = rs.getInt("category_status");
                int cactegoryLevel = rs.getInt("category_level");
                Category category =
                    new Category(categoryID, categoryName, parentID, isEnd, categoryStatus, cactegoryLevel);
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        /*
        dao.insertNewCategory("家用电器", "", 0, 0);
        dao.insertNewCategory("手机", "", 0, 0);
        dao.insertNewCategory("运营商", "", 0, 0);
        dao.insertNewCategory("数码", "", 0, 0);
        dao.insertNewCategory("电脑", "", 0, 0);
        dao.insertNewCategory("电脑整机", "电脑", 0, 1);
        dao.insertNewCategory("电脑配件", "电脑", 0, 1);
        dao.insertNewCategory("外设产品", "电脑", 0, 1);

        dao.insertNewCategory("电视", "家用电器", 0, 1);
        dao.insertNewCategory("空调", "家用电器", 0, 1);
        dao.insertNewCategory("洗衣机", "家用电器", 0, 1);
        dao.insertNewCategory("冰箱", "家用电器", 0, 1);

        dao.insertNewCategory("超薄电视", "电视", 0, 2);
        dao.insertNewCategory("全面屏电视", "电视", 0, 2);
        dao.insertNewCategory("智能电视", "电视", 0, 2);

        dao.insertNewCategory("空调挂机", "空调", 0, 2);
        dao.insertNewCategory("空调柜机", "空调", 0, 2);
        dao.insertNewCategory("中央空调", "空调", 0, 2);
        
        dao.insertNewCategory("男鞋", "", 0, 0);
        dao.insertNewCategory("流行男鞋", "男鞋", 0, 1);
        dao.insertNewCategory("新品推荐", "流行男鞋", 0, 2);
        dao.insertNewCategory("商务休闲鞋", "流行男鞋", 0, 2);
        dao.insertNewCategory("休闲鞋", "流行男鞋", 0, 2);
        */
        dao.insertNewCategory("运动", "", 0, 0);
        dao.insertNewCategory("骑行运动", "运动", 0, 1);
        dao.insertNewCategory("山地车", "骑行运动", 0, 2);
        // dao.deleteCategoryAndItsSubCategoriesByName("运动");
    }

}
