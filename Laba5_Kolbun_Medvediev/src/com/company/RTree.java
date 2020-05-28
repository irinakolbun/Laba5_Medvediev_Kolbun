package com.company;

import java.util.ArrayList;

import static com.company.RectangleArea.MAX_NUMBER;

public class RTree {
    RectangleArea root;

    void printTree(){
        root.printRect();
    }

    ArrayList<Location> getClosetsLocations(Location location, double radius){
        return root.getClosestLocations(location, radius, new ArrayList<>());
    }

    void insertLocation(Location location) {
        if (root == null) {
            RectangleArea rect = new RectangleArea(location, location, location, location);
            rect.addLocation(location);
            root = rect;
            return;
        }
        root.addLocation(location);

    }

    void findLinearLocation(Location location){
        int i=0;
        while(root.locations.get(i).getLatidude()!=location.getLatidude() && root.locations.get(i).getLongtitude()!=location.getLongtitude()){
            i++;
        }

        System.out.println("Linear search (amount of comparisons): " + i);
    }
    void findLocation(Location location){
        System.out.println("Tree Search (amount of comparisons): " + root.find(location,0));
    }

    void restructure(){
        root.restructure();
    }



}
