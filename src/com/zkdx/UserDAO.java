package com.zkdx;
import java.sql.*;
public class UserDAO {
	//String url1="jdbc:mysql://localhost:3306/test";
	String url = "jdbc:mysql://localhost:3306/shopdemo?"
			+ "useUnicode=true&characterEncoding=utf8&"
			+ "serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";

	String SQLusername = "root";
	String SQLpassword = "249658364";
	Connection con = null;
	PreparedStatement ps =null;
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
		con=DriverManager.getConnection(url, SQLusername, SQLpassword);				
}
public void closeAll() {
	if( rs != null)
		try {
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	if(ps != null)
		try {
			ps.close();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	if(con != null)
		try {
			con.close();
		} catch (SQLException e2) {
			
			e2.printStackTrace();
		}
	
}

	public User getUserById(int id) {
		User user = null;
		try {
		this.getConnection();
		String sql = "SELECT* from users where users.id=?";
			ps = con.prepareStatement(sql);
	         rs= ps.executeQuery();
	        
	        while(rs.next()){
	        	int userid = rs.getInt("id");
	        	String username = rs.getString("username");
	        	String userPassword = rs.getString("password"); 
	        	String userPhone = rs.getString("phone");
	        	String userAddress = rs.getString("address"); 
	            
	            user = new User(userid,username,userPassword,userPhone,userAddress);
	            System.out.println(user);
	        }
	        
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeAll();			
		}
		return user;
		
	}
	
	public User getUserByUsername(String name) {
		if (name == null) return null;
		User user = null;
		try {	
			 this.getConnection();
			 String sql = "SELECT* from users where users.username=?";
			 ps = con.prepareStatement(sql);
			 ps.setString(1, name);
	         rs= ps.executeQuery();
	        
	        while (rs.next()){
	        	int userid = rs.getInt("id");
	        	String username = rs.getString("username");
	        	String userPassword = rs.getString("password"); 
	        	String userPhone = rs.getString("phone");
	        	String userAddress = rs.getString("address"); 
	            
	            user = new User(userid,username,userPassword,userPhone,userAddress);
	            System.out.println(user);
	        }
	        
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeAll();
			
		}
		return user;
		
	}
	
	public int modifyUserByUserName(String username,String password,String 
			phone,String address) {
		if(username==null)return 0;
		User user=getUserByUsername(username);
		int ans=0;
		if(user!=null) {
			try {
				this.getConnection();
				 
		         String sql = "update  users set password=?,phone=?,address=?where users.username=?";					 ;
					 ps = con.prepareStatement(sql);					 
					 ps.setString(1, password);
					 ps.setString(2, phone);
					 ps.setString(3, address);
					 ps.setString(4, username);
			         ans= ps.executeUpdate();
		}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				this.closeAll();
				
			}}
		return ans;
		
	}
	public void clearUsers() {
		try {
			this.getConnection();
			
			 String sql="delete from users";
			 ps = con.prepareStatement(sql);
			 ps.executeUpdate();
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeAll();
			
		}
	}
	public int insertNewUser(String username,String password,String phone,String address) {
		int ans=0;
		if(username==null)return 0;
		User user=getUserByUsername(username);
		
		if(user!=null)return 0; 
		try {
		this.getConnection();			 
		 String sql="insert into users (username,password,phone,address)" + 
			 		"values(?,?,?,?)";

			 ps = con.prepareStatement(sql);
			 ps.setString(1, username);
			 ps.setString(2, password);
			 ps.setString(3, phone);
			 ps.setString(4, address);
	         ans = ps.executeUpdate();
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeAll();			
		}
	return ans;
	}
	public void deleteUserByUserName(String name) {
		if(name==null)return ;
		try {
			this.getConnection();
			 String sql = "delete from users where users.username=?";
			 ps = con.prepareStatement(sql);
			 ps.setString(1, name);
			 
			 ps.executeUpdate();
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.closeAll();
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		UserDAO obj=new UserDAO();
		obj.clearUsers();
		obj.insertNewUser("lisi", "768", "18902038771", "Palmont City");
		obj.insertNewUser("张三", "256", "18902038772", "Lincoln Park");
		obj.getUserByUsername("zhangsan");
		obj.getUserByUsername("lisi");
		obj.modifyUserByUserName("张三","123", "7760", "Nevada");
		/*
		obj.deleteUserByUserName("lisi");
		obj.getUserByUsername("zhangsan");		
		obj.getUserByUsername("lisi");
		*/
	}

}
