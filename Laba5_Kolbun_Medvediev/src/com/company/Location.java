package com.company;

import static java.lang.Math.*;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Location {
    // приймати на вході географічну точку (широта, довгота), ціле число N, та тип/підтип шуканого об'єкта
    // Широта; Довгота; Тип; Підтип; Назва; Адреса;
    // широта - latitude; долгота - longitude
    // 44,60105;33,52291;tourism;hotel;Украина;Гоголя улица,2; structure of the file
    // latitude; longitude; activity; specification; country/name of institufion; address;
    final static int EARTH_RADIUS = 6371;
    private double latitude;
    private double longitude;
    public String type;
    public String subType;
    public String name;
    public String address;

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    Location(double latitude, double longitude, String type, String subType, String name, String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.subType = subType;
        this.name = name;
        this.address = address;
    }

//    public String getType() {
//        return this.type;
//    }

    public double getLatidude() {
        return this.latitude;
    }

    public double getLongtitude() {
        return this.longitude;
    }

    public String getBounds(){
        return this.latitude + " " + this.longitude;
    }

    public double getRadius( double x ){
        return x * PI / (double) 180;
    }

//    public double sphericalDistance(Location l2){
//         // km
//        double phi1 = getRadius(this.getLatidude());
//        double phi2 = getRadius(l2.getLatidude());
//        double delta_phi = getRadius(l2.getLatidude() - this.getLatidude());
//        double delta_lambda = getRadius(l2.getLongtitude() - this.getLongtitude());
//        double a = (pow(sin(delta_phi / (double) 2), 2) + (cos(phi1) * cos(phi2)) * (pow(sin(delta_lambda / (double) 2), 2)));
//        double c = 2*(atan2((sqrt(a)), (sqrt(1-a))));
//        return EARTH_RADIUS*c; // distance in km
//    }

    public double sphericalDistance(Location l2){
        return Math.sqrt(Math.pow(l2.getLatidude() - this.getLatidude(),2) + Math.pow(l2.getLongtitude() - this.getLongtitude(),2));
    }

}
