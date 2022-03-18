package com.example.store_online.data_models;

public class Category {
    private String id;
    private String image;
    private String name;

    public Category(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
