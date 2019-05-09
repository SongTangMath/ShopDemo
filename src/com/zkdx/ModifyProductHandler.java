package com.zkdx;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
/**
 * Servlet implementation class ModifyProductHandler
 */
@WebServlet("/ModifyProductHandler")
public class ModifyProductHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyProductHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		FileItemFactory fileItemFactory=new DiskFileItemFactory();
		ServletFileUpload sfu=new ServletFileUpload(fileItemFactory);
		sfu.setHeaderEncoding("utf-8");
		List<FileItem>items=new ArrayList<FileItem>();
		String productname=null;
		try {
			items=sfu.parseRequest(request);
			System.out.println("items.size="+items.size());
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String basePath = request.getScheme() + "://"
	            + request.getServerName() + ":" + request.getServerPort()
	            + request.getContextPath() + "/";
		
		
		ProductDAO dao=new ProductDAO();
		for(FileItem item:items)
			if(item.isFormField()) {
				String itemFieldName=item.getFieldName();
				String value=null;
				value=item.getString("utf-8");
				//System.out.println("fieldName:" + itemFieldName + "--value:" + value);
				//System.out.println(itemFieldName);
				if("productname".equals(itemFieldName))productname=value;				
				
			}
		
		for(FileItem item:items)
			if(item.isFormField()) {
				String itemFieldName=item.getFieldName();
				String value=null;
				value=item.getString("utf-8");
				//System.out.println("fieldName:" + itemFieldName + "--value:" + value);
				//System.out.println(itemFieldName);
				 if("url".equals(itemFieldName)) {
					//System.out.println(value);
					if(!"".contentEquals(value)) {
										
					dao.modifyProductPictureLinkByProductName(productname,value);
					
				}
				}
				 else if("productplan".equals(item.getFieldName())) {
					 System.out.println(productname+"  "+ item.getString("utf-8"));
					 dao.modifyProductPlanByProductName(productname, item.getString("utf-8"));
				 }
			}
		for(FileItem item:items)
			if(!item.isFormField()) {
				String fileName=item.getName();
				System.out.println("fileSize:"+item.getSize());
				if(item.getSize()==0)continue;
				String filePathPart1= getServletContext().getRealPath("/UploadedPictures");
				long time=System.currentTimeMillis();
				String filePathPart2= time+fileName;
				String newProductLink=basePath+"UploadedPictures/"+filePathPart2;
				System.out.println(newProductLink);
				
				//System.out.println("fileName:" + fileName );
				//System.out.println(request.getContextPath());
				
				File file=new File(filePathPart1);
				if(!file.exists())file.mkdirs();
				//System.out.println(file.getAbsolutePath());
				
				file=new File(file,filePathPart2);
				
				System.out.println(file.getAbsolutePath());
				try {
					System.out.println(productname);
					
					item.write(file);
					dao.modifyProductPictureLinkByProductName(productname,newProductLink);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		response.sendRedirect(request.getContextPath()+"/ProductsPlanner.jsp"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
