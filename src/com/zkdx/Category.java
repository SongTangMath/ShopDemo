package com.zkdx;

public class Category {
    private int categoryID;
    private String categoryName;
    private int parentID;
    private int isEnd, categoryStatus;
    private int categoryLevel;
    
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public int getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(int categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Category(int categoryID, String categoryName, int parentID, int isEnd, int categoryStatus,
        int categoryLevel) {
        super();
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.parentID = parentID;
        this.isEnd = isEnd;
        this.categoryStatus = categoryStatus;
        this.categoryLevel = categoryLevel;
    }

    @Override
    public String toString() {
        return "Category [categoryID=" + categoryID + ", categoryName=" + categoryName + ", parentID=" + parentID
            + ", isEnd=" + isEnd + ", categoryStatus=" + categoryStatus + ", categoryLevel=" + categoryLevel + "]";
    }

 

}
