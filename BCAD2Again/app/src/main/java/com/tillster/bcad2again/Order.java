package com.tillster.bcad2again;

public class Order
{
    String orderID;
    String orderDesc;
    String orderShipMethod;

    public Order()
    {
        //default constructor do not TOUCH !!!!!
    }

    public Order(String orderID, String orderDesc, String orderShipMethod) {
        this.orderID = orderID;
        this.orderDesc = orderDesc;
        this.orderShipMethod = orderShipMethod;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderShipMethod() {
        return orderShipMethod;
    }

    public void setOrderShipMethod(String orderShipMethod) {
        this.orderShipMethod = orderShipMethod;
    }


}
