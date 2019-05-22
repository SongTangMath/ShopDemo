package com.zkdx;

import java.util.*;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

/**
 * Servlet implementation class AddProductsFromExcel
 */
@WebServlet("/AddProductsFromExcel")
public class AddProductsFromExcel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductsFromExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(fileItemFactory);
        sfu.setHeaderEncoding("utf-8");
        List<FileItem> items = new ArrayList<FileItem>();

        try {
            items = sfu.parseRequest(request);
            System.out.println("items.size=" + items.size());
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";

        File file = null;
        for (FileItem item : items) {
            if (!item.isFormField()) {
                String fileName = item.getName();
                System.out.println("fileSize:" + item.getSize());
                String filePathPart1 = getServletContext().getRealPath("/UploadedExcels");
                long time = System.currentTimeMillis();
                String filePathPart2 = time + fileName;

                file = new File(filePathPart1);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file, filePathPart2);
                System.out.println(file.getAbsolutePath());
                try {
                    item.write(file);
                } catch (Exception e) {                    
                    e.printStackTrace();
                }
            }
        }
        Workbook workBook = null;
        String path = file.getAbsolutePath();
        if (path.endsWith("xlsx")) {
            try {
                workBook = new XSSFWorkbook(file);
            } catch (InvalidFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (path.endsWith("xls")) {
            workBook = new HSSFWorkbook(new FileInputStream(file));
        }
        if(workBook==null) {
            return;
        }
        Sheet sheet = workBook.getSheetAt(0);
        ProductDAO dao = new ProductDAO();
        DataFormatter formatter = new DataFormatter();
        
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("firstRowNum: " + firstRowNum + "lastRowNum: " + lastRowNum);
        for (int rIndex = firstRowNum; rIndex <= lastRowNum; rIndex++) {

            Row row = sheet.getRow(rIndex);
            if (row == null) {
                continue;
            }
            int firstCellNum = row.getFirstCellNum();
            int lastCellNum = row.getLastCellNum();

            System.out.println("firstCellNum: " + firstCellNum + "lastCellNum: " + lastCellNum);
            if (rIndex == 0) {
                if (firstCellNum != 0 || lastCellNum != 5) {
                    break;
                }
                boolean checkFirstRow = true;
                for (int cIndex = firstCellNum; cIndex <= lastCellNum; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    String text = formatter.formatCellValue(cell);
                    System.out.print(cIndex + " " + text + " ");
                    switch (cIndex) {

                        case 0:
                            if (!"序号".equals(text)) {
                                checkFirstRow = false;
                            }
                            break;
                        case 1:
                            if (!"商品名称".equals(text)) {
                                checkFirstRow = false;
                            }
                            break;
                        case 2:
                            if (!"商品售价".equals(text)) {
                                checkFirstRow = false;
                            }
                            break;
                        case 3:
                            if (!"商品进价".equals(text)) {
                                checkFirstRow = false;
                            }
                            break;
                        case 4:
                            if (!"商品分类".equals(text)) {
                                checkFirstRow = false;
                            }
                            break;
                        default:
                            break;
                    }

                }
                if (!checkFirstRow) {
                    System.out.println("输入文件格式错误");
                    break;
                }
            } else {
                int price = -1, buyingPrice = -1;
                String productname = null, productCategory = null;

                for (int cIndex = firstCellNum; cIndex <= lastCellNum; cIndex++) {
                    Cell cell = row.getCell(cIndex);
                    String text = formatter.formatCellValue(cell);
                    System.out.print(cIndex + " " + text + " ");

                    switch (cIndex) {

                        case 1:
                            productname = text;
                            break;
                        case 2:
                            try {
                                price = Integer.parseInt(text);
                            } catch (NumberFormatException e) {
                                System.out.println("NumberFormatException");
                            }

                            break;
                        case 3:
                            try {
                                buyingPrice = Integer.parseInt(text);
                            } catch (NumberFormatException e) {
                                System.out.println("NumberFormatException");
                            }
                            break;
                        case 4:
                            productCategory = text;
                            break;
                        default:
                            break;
                    }

                }

                if (price == -1 || buyingPrice == -1 || productname == null || "".equals(productname)
                    || productCategory == null || "".equals(productCategory)) {
                    continue;
                }
                dao.insertNewProduct(productname, "", 0, price, "", buyingPrice, productCategory);
            }

        }
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
