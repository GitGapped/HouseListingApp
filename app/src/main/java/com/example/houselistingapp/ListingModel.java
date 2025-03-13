package com.example.houselistingapp;

public class ListingModel {
    private int id;
    private String name, address, description, phone;
    private int squareMeters, yearBuilt;
    private double price;

    public ListingModel(int id, String name, String address, String description, int squareMeters, double price, String phone, int yearBuilt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.squareMeters = squareMeters;
        this.price = price;
        this.phone = phone;
        this.yearBuilt = yearBuilt;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getPrice() { return price; }
}
