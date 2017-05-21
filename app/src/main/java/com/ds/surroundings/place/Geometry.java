package com.ds.surroundings.place;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class Geometry implements Serializable {

    @Key
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location implements Serializable {
        @Key("lat")
        private double latitude;

        @Key("lng")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
