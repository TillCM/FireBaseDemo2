package com.tillster.orderapp;

public class Orders
{
    String product;
    String Qty;

    public Orders()
    {
        //empty constructor do not remove
    }

    public Orders(String product, String qty)
    {

        this.product = product;
        Qty = qty;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String ToString()
    {
        return "Product Name " + product +"/n" + "Product Qty " + Qty ;
    }
}
