package com.company;

public class Main {
    public static void main(String[] args) {
        Location l1 = new Location(100, 200);
        Location l2 = new Location(200, 100);
        System.out.println(l1.sphericalDistance(l2));
    }
}
