package com.data.pooja.objects;

import android.media.Image;

import java.sql.Date;
import java.util.List;

/**
 * Created by Fortune on 1/22/2017.
 */

public class StopSign extends Entity {
    public StopSign(Date dateCreated, float latitude, float longitude, String note, List<String> images) {
        super(dateCreated, latitude, longitude, note, images);
    }

    @Override
    public String toString() {
        return "Stop Sign";
    }
}
