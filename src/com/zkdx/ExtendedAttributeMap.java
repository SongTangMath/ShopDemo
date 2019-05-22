package com.zkdx;

import java.util.*;

public class ExtendedAttributeMap {

    private HashMap<String, ArrayList<String>> attributeValueMap;

    public HashMap<String, ArrayList<String>> getAttributeValueMap() {
        return attributeValueMap;
    }

    public void setAttributeValueMap(HashMap<String, ArrayList<String>> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }

    public ExtendedAttributeMap() {
        attributeValueMap = new HashMap<String, ArrayList<String>>();
    };

    public ExtendedAttributeMap(String productName) {
        attributeValueMap = new HashMap<String, ArrayList<String>>();
        List<ExtendedAttribute> list = new ExtendedAttributeDAO().listAttributesByProductName(productName);
        for (ExtendedAttribute attr : list) {
            ArrayList<String> temp = new ArrayList<String>(Arrays.asList(attr.getAttributeValue().split(" ")));
            attributeValueMap.put(attr.getAttributeName(), temp);
        }
    }

    public ExtendedAttributeMap(int id) {
        attributeValueMap = new HashMap<String, ArrayList<String>>();
        List<ExtendedAttribute> list = new ExtendedAttributeDAO().listAttributesByProductID(id);
        
        for (ExtendedAttribute attr : list) {
            ArrayList<String> temp = new ArrayList<String>(Arrays.asList(attr.getAttributeValue().split(" ")));

            System.out.println(temp);
            attributeValueMap.put(attr.getAttributeName(), temp);
        }
    }

    public static void main(String[] args) {
        ExtendedAttributeMap map = new ExtendedAttributeMap(14);

    }
}
