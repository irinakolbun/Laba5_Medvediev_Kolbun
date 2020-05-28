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


    void restructure(){
        root.restructure();
    }



}
