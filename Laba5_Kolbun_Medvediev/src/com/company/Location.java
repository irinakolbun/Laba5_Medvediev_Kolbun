package com.company;

import static java.lang.Math.*;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Location {
    // приймати на вході географічну точку (широта, довгота), ціле число N, та тип/підтип шуканого об'єкта
    // широта - latitude; долгота - longitude
    // 44,60105;33,52291;tourism;hotel;Украина;Гоголя улица,2; structure of the file
    // latitude; longitude; activity; specification; country/name of institufion; address;

    private String type;
    private double latitude;
    private double longitude;

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getType() {
        return this.type;
    }

    public double getLat() {
        return this.latitude;
    }

    public double getLong() {
        return this.longitude;
    }

    public double getRad( double x ){
        return x * PI / (double) 180;
    }

    public double sphericalDistance(Location l2){
        final int EARTH_RADIUS = 6371; // km
        double phi1 = getRad(this.getLat());
        double phi2 = getRad(l2.getLat());
        double delta_phi = getRad(l2.getLat() - this.getLat());
        double delta_lambda = getRad(l2.getLong() - this.getLong());
        double a = (pow(sin(delta_phi / (double) 2), 2) + (cos(phi1) * cos(phi2)) * (pow(sin(delta_lambda / (double) 2), 2)));
        double c = 2*(atan2((sqrt(a)), (sqrt(1-a))));
        return EARTH_RADIUS*c; // distance in km
    }

}
