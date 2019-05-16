package com.zkdx;

public class SellingStatus {
    private String productName;
    private int quantitySold, totalCost, totalProfit;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    public void addQuantitySold(int quantitySold) {
        this.quantitySold += quantitySold;
    }

    public void addTotalCost(int totalCost) {
        this.totalCost += totalCost;
    }

    public void addTotalProfit(int totalProfit) {
        this.totalProfit += totalProfit;
    }

    public SellingStatus(String productName, int quantitySold, int totalCost, int totalProfit) {
        super();
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalCost = totalCost;
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "SellingStatus [productName=" + productName + ", quantitySold=" + quantitySold + ", totalCost="
            + totalCost + ", totalProfit=" + totalProfit + "]";
    }

}
