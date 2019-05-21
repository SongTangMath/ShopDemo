package com.zkdx;

public class ExtendedAttribute {
    private int productID;
    private String productname, attributeName, attributeValue;
    private int attributeID;
    
    public int getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(int attributeID) {
        this.attributeID = attributeID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public ExtendedAttribute(int productID, String productname, String attributeName, String attributeValue,
        int attributeID) {
        super();
        this.productID = productID;
        this.productname = productname;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.attributeID = attributeID;
    }

    @Override
    public String toString() {
        return "ExtendedAttribute [productID=" + productID + ", productname=" + productname + ", attributeName="
            + attributeName + ", attributeValue=" + attributeValue + ", attributeID=" + attributeID + "]";
    }

   

}
