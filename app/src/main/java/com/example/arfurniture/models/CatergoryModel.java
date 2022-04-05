package com.example.arfurniture.models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatergoryModel implements Serializable {
    String TAG="CatergoryModel";
    String id, name, image,description,i1,i2,i3,i4;
    int price;


    public String getI1() {
        return i1;
    }

    public String getI3() {
        return i3;
    }

    public String getI4() {
        return i4;
    }

    public String getI2() {
        return i2;
    }

    public String getDescription() {
        return description;
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
