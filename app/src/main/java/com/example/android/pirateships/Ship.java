package com.example.android.pirateships;

public class Ship {
    private String imageURL;
    private String title;
    private String price;
    private String description;
    private int id;

    public Ship(int id, String imageURL, String title, String price) {
        this.id = id;
        this.imageURL = imageURL;
        this.title = title;
        this.price = price;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

}
