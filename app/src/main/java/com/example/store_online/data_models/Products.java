package com.example.store_online.data_models;

public class Products {
    private String id;
    private String name;
    private String image;
    private double star;
    private String category;
    private int price;
    private String description;
    private int evaluate;
    private int sold;

    public Products() {
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Products(String id, String name, String image, double star, String category, int price, String description, int evaluate, int sold) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.star = star;
        this.category = category;
        this.price = price;
        this.description = description;
        this.evaluate = evaluate;
        this.sold = sold;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
