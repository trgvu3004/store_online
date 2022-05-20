package com.example.store_online.data_models;

public class ProductsSeen {
    private String id;
    private String name;
    private Integer price;
    private String image;
    private Double star;

    public ProductsSeen() {
    }

    public ProductsSeen(String id, String name, Integer price, String image, Double star) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.star = star;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }
}
