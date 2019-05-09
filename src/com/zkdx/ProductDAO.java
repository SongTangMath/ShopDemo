package com.zkdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDAO {
	//String url1="jdbc:mysql://localhost:3306/test";
		String url="jdbc:mysql://localhost:3306/shopdemo?"
				+ "useUnicode=true&characterEncoding=utf8&"
				+ "serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";

		String SQLusername="root";
		String SQLpassword="249658364";
		Connection con=null;
		Statement stmt=null;
		PreparedStatement ps =null;
		ResultSet rs=null;
		static {   
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new ExceptionInInitializerError(e);
			}
		}
		public Product getProductById(int id) {
			Product product=null;
			try {
				
				DriverManager.getConnection(url, SQLusername, SQLpassword);
				con=DriverManager.getConnection(url, SQLusername, SQLpassword);
				//System.out.print(con);
				 stmt = con.createStatement();
		         rs= stmt.executeQuery("SELECT* from productinfo where productinfo.id="+id);
		        
		        while(rs.next()){
		        	
		        	String productname=rs.getString("productname");
		        	String pictureLink=rs.getString("pictureLink"); 
		        	int productid=rs.getInt("id");
		        	int inventoryQuantity=rs.getInt("inventoryQuantity");
		        	int price=rs.getInt("price"); 
		        	String productPlan=rs.getString("productplan");
		             product=new Product(productname,pictureLink,productid,
		            		inventoryQuantity,price,productPlan	);
		            System.out.print(product);
		            
		        }
		        
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if( rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(stmt!=null)
					try {
						stmt.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			}
			return product;
			
		}
		
		public Product getProductByProductName(String name) {
			if(name==null)return null;
			Product product=null;
			
			try {	DriverManager.getConnection(url, SQLusername, SQLpassword);
					con=DriverManager.getConnection(url, SQLusername, SQLpassword);				
					 String sql="select*from productinfo where productinfo.productname=?" ;
							 
							 ps = con.prepareStatement(sql);
							 
							 ps.setString(1, name);						 
							 
					         rs= ps.executeQuery();
					         while(rs.next()) {
					        	  String productname=rs.getString(2), pictureLink=rs.getString(5);
					        	  int id=rs.getInt(1),inventoryQuantity=rs.getInt(4),
					        	   price=rs.getInt(3);
					        	  String productplan=rs.getString(6);
					        	  product= new Product(productname,pictureLink,id,inventoryQuantity,
					        			  price,productplan);
					        	  System.out.println(product);
					         }
		        
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(stmt!=null)
					try {
						stmt.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			}
			return product;
			
		}
		
		public int modifyProductByProductName(String productname, String pictureLink, 
				int inventoryQuantity, int price,String productplan) {
			if(productname==null)return 0;
			Product product=getProductByProductName(productname);
			int ans=0;
			if(product!=null) {
				try {					
					DriverManager.getConnection(url, SQLusername, SQLpassword);
					con=DriverManager.getConnection(url, SQLusername, SQLpassword);
					
					 String sql="update productinfo set pictureLink=?,"
					 		+ "inventoryQuantity=?,price=?,productplan=? where productname=?" ;
						 
						 ps = con.prepareStatement(sql);
						 
						 ps.setString(1, pictureLink);						 
						 ps.setInt(2, inventoryQuantity);
						 ps.setInt(3, price);
						 ps.setString(4, productplan);
						 ps.setString(5, productname);
				         ans= ps.executeUpdate();
			         
			}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if(ps!=null)
						try {
							ps.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(con!=null)
						try {
							con.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					
				}}
			return ans;
			
		}
		
		public int modifyProductPictureLinkByProductName(String productname, String pictureLink){
			if(productname==null)return 0;
			Product product=getProductByProductName(productname);
			//System.out.println(product);
			int ans=0;
			if(product!=null) {
				try {					
					DriverManager.getConnection(url, SQLusername, SQLpassword);
					con=DriverManager.getConnection(url, SQLusername, SQLpassword);
					
					 String sql="update productinfo set pictureLink=? where productname=?";
					 
						 
						 ps = con.prepareStatement(sql);
						 
						 ps.setString(1, pictureLink);						 
						 ps.setString(2, productname);
				         ans= ps.executeUpdate();
			         
			}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if(ps!=null)
						try {
							ps.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(con!=null)
						try {
							con.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					
				}}
			return ans;
			
		}
		
		public int modifyProductPlanByProductName(String productname, String productPlan){
			if(productname==null)return 0;
			Product product=getProductByProductName(productname);
			//System.out.println(product);
			int ans=0;
			if(product!=null) {
				try {					
					DriverManager.getConnection(url, SQLusername, SQLpassword);
					con=DriverManager.getConnection(url, SQLusername, SQLpassword);
					
					 String sql="update productinfo set productplan=? where productname=?";
					 
						 
						 ps = con.prepareStatement(sql);
						 
						 ps.setString(1, productPlan);						 
						 ps.setString(2, productname);
				         ans= ps.executeUpdate();
			         
			}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if(ps!=null)
						try {
							ps.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(con!=null)
						try {
							con.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					
				}}
			return ans;
			
		}
		public int modifyProductIntentoryQuantityByProductId(int id, int number){
			
			Product product=getProductById(id);
			//System.out.println(product);
			int ans=0;
			if(product!=null) {
				try {					
					DriverManager.getConnection(url, SQLusername, SQLpassword);
					con=DriverManager.getConnection(url, SQLusername, SQLpassword);
					
					 String sql="update productinfo set inventoryQuantity=? where id=?";
					 
						 
						 ps = con.prepareStatement(sql);						 
						 ps.setInt(2, id);		
						 int newIntentoryQuantity=number+product.getInventoryQuantity();
						 if(newIntentoryQuantity<0)newIntentoryQuantity=0;
						 ps.setInt(1, newIntentoryQuantity);
				         ans= ps.executeUpdate();
			         
			}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					if(ps!=null)
						try {
							ps.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(con!=null)
						try {
							con.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					
				}}
			return ans;
			
		}
		
		public void clearProducts() {
			try {				
				DriverManager.getConnection(url, SQLusername, SQLpassword);
				con=DriverManager.getConnection(url, SQLusername, SQLpassword);
				 stmt = con.createStatement();
				 String sql="delete from productinfo";
				 stmt.execute(sql);
		}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(stmt!=null)
					try {
						stmt.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			}
		}
		public int insertNewProduct(String productname, String pictureLink, 
				 int inventoryQuantity, int price,String productplan) {
			if(productname==null)return 0;
			int ans=0;
			Product product=getProductByProductName(productname);
			
			if(product!=null)return 0; 
			try {				
				DriverManager.getConnection(url, SQLusername, SQLpassword);
				con=DriverManager.getConnection(url, SQLusername, SQLpassword);
				 stmt = con.createStatement();
				 String sql="insert into productinfo (productname,pictureLink,"
				 		+ "inventoryQuantity,price,productplan)" + 
					 		"values(?,?,?,?,?)";
					 
					 ps = con.prepareStatement(sql);
					 ps.setString(1, productname);
					 ps.setString(2, pictureLink);					 
					 ps.setInt(3, inventoryQuantity);
					 ps.setInt(4, price);
					 ps.setString(5,productplan);
			         ans= ps.executeUpdate();
		         
		}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(ps!=null)
					try {
						ps.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			}
		return ans;
		}
		public void deleteProductByProductName(String name) {
			if(name==null)return;
			try {
				
				DriverManager.getConnection(url, SQLusername, SQLpassword);
				con=DriverManager.getConnection(url, SQLusername, SQLpassword);
				 stmt = con.createStatement();
				 String sql="delete from product where productinfo.productname='"+name+"'";
				 stmt.execute(sql);
		}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(stmt!=null)
					try {
						stmt.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(con!=null)
					try {
						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			}
		}
		public List<Product>getAllProducts(){
			List<Product>list=new ArrayList<Product>();
			try {
				DriverManager.getConnection(url, SQLusername, SQLpassword);
				con=DriverManager.getConnection(url, SQLusername, SQLpassword);
			
			 stmt = con.createStatement();
	         rs= stmt.executeQuery("SELECT* from productinfo");
	        
	        while(rs.next()){
	        	String productname=rs.getString("productname");
	        	String pictureLink=rs.getString("pictureLink"); 
	        	int productid=rs.getInt("id");
	        	int inventoryQuantity=rs.getInt("inventoryQuantity");
	        	int price=rs.getInt("price"); 
	        	String productPlan=rs.getString("productplan");
	             Product temp=new Product(productname,pictureLink,productid,
	            		inventoryQuantity,price,productPlan	);
	            list.add(temp);
	        }
	        
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
		}
		return list;
			
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductDAO obj=new ProductDAO();
		/*
		obj.clearProducts();
		obj.insertNewProduct("红米Redmi Note7",
		"//img13.360buyimg.com/n1/s450x450_jfs/t1/9085/2/12381/146200/5c371c5bE08328383/4f4ba51aed682207.jpg",
				10, 1200);
		List<Product>list=obj.getAllProducts();
		
		for(Product temp:list)System.out.print(temp);
		Product product=obj.getProductByProductName("红米Redmi Note7");
		System.out.println(product);*/
		obj.insertNewProduct("华为MagicBook 2019", "https://img11.360buyimg.com/cms/jfs/t1/32635/15/9955/193607/5cac060cEa590420b/fe8ad1d5ea5f9f98.png", 
				12, 4299, "");
		
	}

}
