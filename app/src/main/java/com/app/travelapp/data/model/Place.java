package com.app.travelapp.data.model;

import java.util.List;

public class Place {

    String placeId;
    String name;
    String description;
    String address;
    List<String> images;
    User author;

    public Place(String placeId, String name, String description, String address, List<String> images, User author) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.images = images;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
