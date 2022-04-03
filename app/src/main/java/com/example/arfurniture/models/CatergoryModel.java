package com.example.arfurniture.models;

import java.io.Serializable;

public class CatergoryModel implements Serializable {
    String id, name, image,description;
    int price;
    String[] images;


    public String getDescription() {
        return description;
    }

    public String[] getImages() {
        return images;
    }

    public String getId() { return id; }

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
