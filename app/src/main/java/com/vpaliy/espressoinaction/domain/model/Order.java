package com.vpaliy.espressoinaction.domain.model;


import java.util.Date;
import java.util.List;

public class Order {

    private int orderId;
    private List<Coffee> coffees;
    private Date pickUpTime;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }
}
