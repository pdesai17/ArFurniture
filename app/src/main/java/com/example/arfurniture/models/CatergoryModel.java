package com.example.arfurniture.models;

public class CatergoryModel {
    String name, image;
    int price;
    CatergoryModel()
    {

    }
    CatergoryModel(String name,String image,int price)
    {
        this.name=name;
        this.image=image;
        this.price=price;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
