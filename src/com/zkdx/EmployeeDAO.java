package com.zkdx;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class EmployeeDAO {
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
			con=DriverManager.getConnection(url, SQLusername, SQLpassword);				
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
	public Employee getEmployeeById(int id) {
		Employee employee = null;
		String sql = "SELECT* from employee where employee.id=?";
		try {
			getConnection();
			 ps = con.prepareStatement(sql);
			 ps.setInt(1, id);
	         rs = ps.executeQuery();
	        
	        while(rs.next()){
	        	
	        	String name = rs.getString("name");
	        	String password = rs.getString("password"); 
	        	String departmentName = rs.getString("departmentname"); 
	        	String job = rs.getString("job"); 
	        	int salary = rs.getInt("salary"); 
	             employee = new Employee( id,  name,  password,  departmentName,  job,  salary);	            
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
	        return employee;
	}
	
	public List<Employee> listAllEmployees() {
		Employee employee = null;
		List<Employee>list = new ArrayList<Employee>();
		String sql = "SELECT* from employee";
		try {
			getConnection();
			 ps = con.prepareStatement(sql);
			 
	         rs= ps.executeQuery();
	        
	        while (rs.next()){
	        	int id = rs.getInt("id");
	        	String name = rs.getString("name");
	        	String password = rs.getString("password"); 
	        	String departmentName = rs.getString("departmentname"); 
	        	String job = rs.getString("job"); 
	        	int salary = rs.getInt("salary"); 
	             employee = new Employee( id,  name,  password,  departmentName,  job,  salary);	           
	            list.add(employee);
	        }
	        Collections.sort(list,(a,b)->a.getId()-b.getId());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
	        return list;
	}
	public int clearEmployees() {
		String sql = "delete from employee";
		int ans = 0;
		try {
			getConnection();
			 ps = con.prepareStatement(sql);			 
	          ans=ps.executeUpdate();      
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
		return ans;
	}
	public int insertNewEmployee(int id, String name, String password,
			String departmentName, String job, int salary) {
		int ans = 0;
		String sql="insert into  employee(id,name,password,departmentname,job,salary)"
				+ "values(?,?,?,?,?,?) ";
		try {
			getConnection();
			 ps = con.prepareStatement(sql);
			 ps.setInt(1, id);
			 ps.setString(2, name);
			 ps.setString(3, password);
			 ps.setString(4, departmentName);
			 ps.setString(5, job);
			 ps.setInt(6, salary);
			 ans= ps.executeUpdate();           
	        }
		
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
		
	        return ans;
	       
	}
	
	public int modifyEmployeeById(int id, String name, String password,
			String departmentName, String job, int salary) {
		int ans = 0;
		String sql = "update  employee set name=?,password=?,"
				+ "departmentname=?,job=?,salary=? where employee.id=?";
				
		try {
			getConnection();
			 ps = con.prepareStatement(sql);			
			 ps.setString(1, name);
			 ps.setString(2, password);
			 ps.setString(3, departmentName);
			 ps.setString(4, job);
			 ps.setInt(5, salary);
			 ps.setInt(6, id);
			 ans = ps.executeUpdate();           
	        }
		
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
	        return ans;
	}
	public int deleteEmployeeById(int id) {
		int ans = 0;
		String sql = "delete from  employee where employee.id=?";
		try {
			getConnection();
			 ps = con.prepareStatement(sql);
			 ps.setInt(1, id);			
			 ans = ps.executeUpdate();           
	        }		
		catch(Exception e) {
			e.printStackTrace();
		}
			finally {
				closeAll();
			}
	        return ans;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmployeeDAO dao = new EmployeeDAO();
		dao.clearEmployees();
		dao.insertNewEmployee(1, "张三", "123", "销售部", "售货员", 1000);
		dao.insertNewEmployee(2, "李四", "123", "采购部", "采购员", 2000);
		dao.insertNewEmployee(3, "王五", "123", "人事部", "HR专员", 2000);
	}

}
