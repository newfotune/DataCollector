package com.data.pooja.objects;

import java.sql.Date;
import java.util.List;

/**
 * Created by Fortune on 1/22/2017.
 * @version 1.0
 */

public abstract class Entity {
    private Date dateCreated;
    private float latitude;
    private float longitude;
    private String note;
    private List<String> images;

    public Entity(Date dateCreated, float latitude, float longitude, String note, List<String> images) {
        if (dateCreated == null || images == null)
            throw new IllegalArgumentException("No nulls allowed");

        this.dateCreated = dateCreated;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        if (Float.compare(entity.getLatitude(), getLatitude()) != 0) return false;
        if (Float.compare(entity.getLongitude(), getLongitude()) != 0) return false;
        if (!getDateCreated().equals(entity.getDateCreated())) return false;
        if (getNote() != null ? !getNote().equals(entity.getNote()) : entity.getNote() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getDateCreated().hashCode();
        result = 31 * result + (getLatitude() != +0.0f ? Float.floatToIntBits(getLatitude()) : 0);
        result = 31 * result + (getLongitude() != +0.0f ? Float.floatToIntBits(getLongitude()) : 0);
        result = 31 * result + (getNote() != null ? getNote().hashCode() : 0);
        return result;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public Date getDateCreated() {

        return dateCreated;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getNote() {
        return note;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<String> getImages() {
        return images;
    }
}
