package com.vpaliy.espressoinaction.domain.model;

public class Order {

    private int orderId;
    private Coffee coffee;
    private String name;
    private String pickUpDay;
    private String pickUpTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public void setPickUpDay(String pickUpDay) {
        this.pickUpDay = pickUpDay;
    }

    public String getPickUpDay() {
        return pickUpDay;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public Coffee getCoffee() {
        return coffee;
    }
}
