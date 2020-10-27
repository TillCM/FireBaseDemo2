package com.tillster.firebasedemobcad2;

public class Product
{
    String productName;
    String productID;

    public Product()
    {
        //default constructor
    }

    public Product(String productName, String productID) {
        this.productName = productName;
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

}
