package com.vpaliy.espressoinaction.domain.model;


public class Coffee {

    private double price;
    private int coffeeId;
    private Sweetness sweetness;
    private MilkType milkType;
    private SizeType sizeType;
    private CoffeeType coffeeType;
    private String imageUrl;

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    public int getCoffeeId() {
        return coffeeId;
    }

    public void setMilkType(MilkType milkType) {
        this.milkType = milkType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    public void setSweetness(Sweetness sweetness) {
        this.sweetness = sweetness;
    }

    public double getPrice() {
        return price;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public MilkType getMilkType() {
        return milkType;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public Sweetness getSweetness() {
        return sweetness;
    }
}
