package com.example.store_online.data_models;

public class FeaturedCategory {
    private int resourceID;
    private String name;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeaturedCategory(int resourceID, String name) {
        this.resourceID = resourceID;
        this.name = name;
    }


}
