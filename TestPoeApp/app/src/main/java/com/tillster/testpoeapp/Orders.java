package com.tillster.testpoeapp;

public class Orders
{
    String orderId;
    String customerName;
    String productOrderd;
    String shippingMethod;

    public Orders()
    {
        //default constructor
    }

    public Orders(String orderId, String customerName, String productOrderd, String shippingMethod) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productOrderd = productOrderd;
        this.shippingMethod = shippingMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductOrderd() {
        return productOrderd;
    }

    public void setProductOrderd(String productOrderd) {
        this.productOrderd = productOrderd;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
