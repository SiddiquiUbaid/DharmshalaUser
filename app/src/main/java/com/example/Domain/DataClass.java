package com.example.Domain;

public class DataClass {
    String heading, description;
    Integer drawableImageId;


    public DataClass(String heading, String description) {
        this.heading = heading;
        this.description = description;
    }

    public DataClass(Integer drawableImageId, String heading) {
        this.heading = heading;
        this.drawableImageId = drawableImageId;
    }




    public String getHeading() {
        return heading;
    }

    public DataClass(String heading, String description, Integer drawableImageId) {
        this.heading = heading;
        this.description = description;
        this.drawableImageId = drawableImageId;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDrawableImageId() {
        return drawableImageId;
    }

    public void setDrawableImageId(Integer drawableImageId) {
        this.drawableImageId = drawableImageId;
    }

    public DataClass() { }






}