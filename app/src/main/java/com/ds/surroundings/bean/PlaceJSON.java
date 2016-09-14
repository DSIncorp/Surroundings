package com.ds.surroundings.bean;


import java.util.List;

public class PlaceJSON {
    private String name;
    private List<PlaceTypeJSON> placeTypes;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaceTypeJSON> getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(List<PlaceTypeJSON> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
