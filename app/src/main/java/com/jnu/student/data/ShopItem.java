package com.jnu.student.data;

public class ShopItem {

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    private final String name;
    private final double price;
    private final int imageResourceId;
    public ShopItem(String name_, double price_,int imageId_) {
        this.name = name_;
        this.price = price_;
        this.imageResourceId = imageId_;
    }
}
