package com.ds.surroundings.place.container;

import com.ds.surroundings.place.Place;
import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PlaceList extends Observable implements Serializable {

    @Key
    private String status;
    @Key("results")
    private List<Place> places;

    public PlaceList() {
        places = new ArrayList<>();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        setChanged();
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
