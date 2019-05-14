 package com.zkdx;

import java.sql.Date;

public class OrderInfo {
     private String username,orderid;
     private java.sql.Date date;
     private String productname;
     private int productNumber,price;
     
    public int getProductNumber() {
        return productNumber;
    }
    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public java.sql.Date getDate() {
        return date;
    }
    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    public String getProductname() {
        return productname;
    }
    public void setProductname(String productname) {
        this.productname = productname;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public OrderInfo(String username, String orderid, Date date, String productname, int productNumber, int price) {
        super();
        this.username = username;
        this.orderid = orderid;
        this.date = date;
        this.productname = productname;
        this.productNumber = productNumber;
        this.price = price;
    }
    @Override
    public String toString() {
        return "OrderInfo [username=" + username + ", orderid=" + orderid + ", date=" + date + ", productname="
            + productname + ", productNumber=" + productNumber + ", price=" + price + "]";
    }
    
     
}
