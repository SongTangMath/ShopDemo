package com.zkdx;

public class Product {
private String productname, pictureLink;
private int id,inventoryQuantity;
private int price;
private String productplan;
public String getProductplan() {
	return productplan;
}
public void setProductplan(String productplan) {
	this.productplan = productplan;
}
public String getProductname() {
	return productname;
}
public void setProductname(String productname) {
	this.productname = productname;
}
public String getPictureLink() {
	return pictureLink;
}
public void setPictureLink(String pictureLink) {
	this.pictureLink = pictureLink;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getInventoryQuantity() {
	return inventoryQuantity;
}
public void setInventoryQuantity(int inventoryQuantity) {
	this.inventoryQuantity = inventoryQuantity;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public Product(String productname, String pictureLink, int id, int inventoryQuantity, int price, String productplan) {
	super();
	this.productname = productname;
	this.pictureLink = pictureLink;
	this.id = id;
	this.inventoryQuantity = inventoryQuantity;
	this.price = price;
	this.productplan = productplan;
}
@Override
public String toString() {
	return "Product [productname=" + productname + ", pictureLink=" + pictureLink + ", id=" + id
			+ ", inventoryQuantity=" + inventoryQuantity + ", price=" + price + ", productplan=" + productplan + "]";
}


}
