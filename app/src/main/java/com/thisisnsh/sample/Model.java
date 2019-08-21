package com.thisisnsh.sample;

public class Model {

    String image;
    String name;
    float amount;
    int price;
    String button;

    public Model(String button, String image, String name, float amount, int price) {
        this.image = image;
        this.button = button;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
