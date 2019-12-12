package com.example.gyminventoryapp;

public class GymItem {
    String imageString;
    String itemName;
    int itemCount;

    public GymItem(String imageString, String itemName) {
        this.imageString = imageString;
        this.itemName = itemName;
        this.itemCount = 0;
    }

    public GymItem(String imageString, String itemName, int itemCount) {
        this.imageString = imageString;
        this.itemName = itemName;
        this.itemCount = itemCount;
    }

    public String ItemString() {
        return itemName;
    }

    public String inventoryString() {
        return itemName + ": " + itemCount;
    }
}
