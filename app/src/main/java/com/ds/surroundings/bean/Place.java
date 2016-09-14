package com.ds.surroundings.bean;

import java.util.List;


public class Place {
    private String id;
    private String scope;
    private String name;
    private double latitude;
    private double length;
    private String icon;
    private List<String> types;
    private String vicinity;

    public Place(String id, String scope, String name, double latitude, double length, String icon,
                 List<String> types, String vicinity) {
        this.id = id;
        this.scope = scope;
        this.name = name;
        this.latitude = latitude;
        this.icon = icon;
        this.length = length;
        this.types = types;
        this.vicinity = vicinity;

    }

    public Place() {
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }


}
