package com.example.store_online.data_models;

public class Address {
    private String id;
    private String name;
    private String phone;
    private String home;
    private String address;

    public Address() {
    }

    public Address(String id, String name, String phone, String home, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.home = home;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
