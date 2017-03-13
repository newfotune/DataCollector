package com.data.pooja.objects;

import android.media.Image;

import java.sql.Date;
import java.util.List;

/**
 * Created by Fortune on 1/22/2017.
 */

public class Resturant extends Entity {
    private String brand;

    public Resturant(Date dateCreated, float latitude, float longitude, String note, List<String> images, String brand) {
        super(dateCreated, latitude, longitude, note, images);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resturant ");
        sb.append(brand);
        return sb.toString();
    }

}
