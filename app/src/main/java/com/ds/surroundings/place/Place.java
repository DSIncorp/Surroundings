package com.ds.surroundings.place;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class Place implements Serializable {
    private static final long serialVersionUID = 8302548410710899475L;

    @Key
    private String id;
    @Key
    private String name;
    @Key
    private String reference;
    @Key
    private String icon;
    @Key
    private String vicinity;
    @Key
    private Geometry geometry;

    private PlaceDetails details;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public PlaceDetails getDetails() {
        return details;
    }

    public void setDetails(PlaceDetails details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return name + " - " + id + " - " + reference;
    }
}
